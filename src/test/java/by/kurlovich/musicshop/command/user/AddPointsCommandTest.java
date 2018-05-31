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

public class AddPointsCommandTest {

    @Mocked
    UserReceiver userReceiver;

    @Mocked
    HttpServletRequest request;

    @Test
    public void testUnAuthorisedUser() throws CommandException {
        AddPointsCommand command = new AddPointsCommand(userReceiver);

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
    public void testMinTariffAdd() throws CommandException, ReceiverException {
        AddPointsCommand command = new AddPointsCommand(userReceiver);
        final int startPoints = 100;
        User user = createUser(startPoints);

        new Expectations(command) {{
            command.isAuthorised(withNotNull());
            result = true;
            command.getTariffSubmitted(withNotNull());
            result = Tariff.MIN;
            command.getCurrentUser(withNotNull());
            result = user;
            userReceiver.updateUser(withNotNull());
            result = true;
        }};

        CommandResult result = command.execute(request);

        new Verifications() {{
            request.setAttribute("message", withNotNull());
            times = 0;
            userReceiver.updateUser(withNotNull());
            times = 1;
        }};

        Assert.assertEquals(result.getPage(), PageStore.POINTS_PAGE.getPageName());
        Assert.assertEquals(result.getResponseType(), CommandResult.ResponseType.REDIRECT);
        Assert.assertEquals(user.getPoints(), startPoints + Tariff.MIN.getPoints());
    }

    @Test
    public void testOptTariffAdd() throws CommandException, ReceiverException {
        AddPointsCommand command = new AddPointsCommand(userReceiver);
        final int startPoints = 100;
        User user = createUser(startPoints);

        new Expectations(command) {{
            command.isAuthorised(withNotNull());
            result = true;
            command.getTariffSubmitted(withNotNull());
            result = Tariff.OPT;
            command.getCurrentUser(withNotNull());
            result = user;
            userReceiver.updateUser(withNotNull());
            result = true;
        }};

        CommandResult result = command.execute(request);

        new Verifications() {{
            request.setAttribute("message", withNotNull());
            times = 0;
            userReceiver.updateUser(withNotNull());
            times = 1;
        }};

        Assert.assertEquals(result.getPage(), PageStore.POINTS_PAGE.getPageName());
        Assert.assertEquals(result.getResponseType(), CommandResult.ResponseType.REDIRECT);
        Assert.assertEquals(user.getPoints(), startPoints + Tariff.OPT.getPoints());
    }

    @Test
    public void testMaxTariffAdd() throws CommandException, ReceiverException {
        AddPointsCommand command = new AddPointsCommand(userReceiver);
        final int startPoints = 100;
        User user = createUser(startPoints);

        new Expectations(command) {{
            command.isAuthorised(withNotNull());
            result = true;
            command.getTariffSubmitted(withNotNull());
            result = Tariff.MAX;
            command.getCurrentUser(withNotNull());
            result = user;
            userReceiver.updateUser(withNotNull());
            result = true;
        }};

        CommandResult result = command.execute(request);

        new Verifications() {{
            request.setAttribute("message", withNotNull());
            times = 0;
            userReceiver.updateUser(withNotNull());
            times = 1;
        }};

        Assert.assertEquals(result.getPage(), PageStore.POINTS_PAGE.getPageName());
        Assert.assertEquals(result.getResponseType(), CommandResult.ResponseType.REDIRECT);
        Assert.assertEquals(user.getPoints(), startPoints + Tariff.MAX.getPoints());
    }

    private User createUser(int points) {
        User user = new User();
        user.setPoints(points);
        return user;
    }
}
