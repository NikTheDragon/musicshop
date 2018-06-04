package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.web.pages.PageStore;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

public class BuyAlbumCommandTest {

    @Mocked
    UserReceiver userReceiver;

    @Mocked
    HttpServletRequest request;

    @Test
    public void testUnAuthorisedUser() throws CommandException {
        BuyAlbumCommand command = new BuyAlbumCommand(userReceiver);

        new Expectations(command) {{
            command.getUserRole(withNotNull());
            result = "not_valid_role";
        }};

        CommandResult result = command.execute(request);

        new Verifications() {{
            command.isAuthorised(withNotNull());
            times = 1;
            request.setAttribute("message", "denied");
            times = 1;
        }};

        Assert.assertEquals(result.getPage(), PageStore.ERROR_PAGE.getPageName());
        Assert.assertEquals(result.getResponseType(), CommandResult.ResponseType.FORWARD);
    }

    @Test
    public void testInvalidBuyAlbum() throws CommandException, ReceiverException {
        BuyAlbumCommand command = new BuyAlbumCommand(userReceiver);
        User user = createUser();

        new Expectations(command) {{
            command.isAuthorised(withNotNull());
            result = true;
            command.getCurrentUser(withNotNull());
            result = user;
            request.getParameter("album_id");
            result = "1";
            request.getParameter("album_price");
            result = "1";
            userReceiver.buyAlbum(withNotNull(), "1", 1);
            result = "false";
        }};

        CommandResult result = command.execute(request);

        new Verifications() {{
            request.setAttribute("message", "false");
            times = 1;
            request.getSession(true).setAttribute("albumList", withNotNull());
            times = 0;
            request.getSession(true).setAttribute("user", user);
            times = 0;
            command.buyAlbumTracks(withNotNull(), user);
            times = 0;
        }};

        Assert.assertEquals(result.getPage(), PageStore.ERROR_PAGE.getPageName());
        Assert.assertEquals(result.getResponseType(), CommandResult.ResponseType.FORWARD);
    }

    @Test
    public void testValidBuyAlbum() throws CommandException, ReceiverException {
        BuyAlbumCommand command = new BuyAlbumCommand(userReceiver);
        User user = createUser();

        new Expectations(command) {{
            command.isAuthorised(withNotNull());
            result = true;
            command.getCurrentUser(withNotNull());
            result = user;
            request.getParameter("album_id");
            result = "1";
            request.getParameter("album_price");
            result = "1";
            userReceiver.buyAlbum(withNotNull(), "1", 1);
            result = "true";
        }};

        CommandResult result = command.execute(request);

        new Verifications() {{
            request.setAttribute("message", "true");
            times = 0;
            request.getSession(true).setAttribute("albumList", withNotNull());
            times = 1;
            request.getSession(true).setAttribute("user", user);
            times = 1;
            command.buyAlbumTracks(withNotNull(), user);
            times = 1;
        }};

        Assert.assertEquals(result.getPage(), PageStore.SHOW_ALBUMS_PAGE.getPageName());
        Assert.assertEquals(result.getResponseType(), CommandResult.ResponseType.REDIRECT);
    }

    private User createUser() {
        User user = new User();
        return user;
    }
}
