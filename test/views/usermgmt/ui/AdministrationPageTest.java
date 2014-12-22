package views.usermgmt.ui;

import models.usermgmt.Role;
import org.junit.Before;
import org.junit.Test;
import usermgmt.YAML;
import views.usermgmt.AbstractUITest;
import views.usermgmt.ui.pages.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static usermgmt.Parameters.*;
import static views.usermgmt.ui.pages.AdministrationPageDialog.*;
import static views.usermgmt.ui.pages.AdministrationPageDialog.USER_NOT_FOUND;
import static views.usermgmt.ui.pages.Status.*;

public class AdministrationPageTest extends AbstractUITest{

    @Override
    @Before
    public void setUp(){
        super.setUp();
        YAML.GENERAL_USERS.load();
    }

    @Test
    public void onlyAdminHasAccessToAdministrationPage() {
        UsersPage usersPage = new UsersPage(getBrowser());

        login(FIRST_USER_NAME, FIRST_USER_PASSWORD);
        goTo(UsersPage.URL);

        assertTrue(usersPage.isAt());
        assertTrue(usersPage.checkStatus(UNAUTHORIZED));
    }

    @Test
    public void adminCanOpenAdministrationPage() {
        UsersPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, UsersPage.class);

        assertTrue(usersPage.isAt());
    }

    @Test
    public void adminCanDeleteUser() {
        UsersPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, UsersPage.class);
        int usersCount = usersPage.getUsersCount();

        AdministrationPageUser user = usersPage.getUserByUserName(FIRST_USER_NAME);
        user.delete();

        assertFalse(user.isPresent());
        assertThat(usersPage.getUsersCount(), is(--usersCount));
    }

    @Test
    public void adminCanAddNewUser() {
        UsersPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, UsersPage.class);
        int usersCount = usersPage.getUsersCount();

        AdministrationPageDialog dialog = usersPage.addNewUser().fillCreateDialog(NEW_USER_NAME, NEW_USER_NAME, FIRST_USER_PASSWORD, Role.USER);
        dialog.save(SUCCESS_MESSAGE);

        assertThat(usersPage.getUsersCount(), is(++usersCount));
        assertTrue(usersPage.getUserByUserName(NEW_USER_NAME).isPresent());
    }

    @Test
    public void adminCanAddNewUserWithoutSettingDirectlyFullName() {
        UsersPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, UsersPage.class);
        int usersCount = usersPage.getUsersCount();

        AdministrationPageDialog dialog = usersPage.addNewUser().fillCreateDialog(NEW_USER_NAME, null, FIRST_USER_PASSWORD, Role.USER);
        dialog.save(SUCCESS_MESSAGE);
        AdministrationPageUser user = usersPage.getUserByUserName(NEW_USER_NAME);

        assertThat(usersPage.getUsersCount(), is(++usersCount));
        assertTrue(user.isPresent());
        assertTrue(user.getFullName().equals(NEW_USER_NAME));
    }

    @Test
    public void adminCanNotAddNewUserWithExistingName() {
        UsersPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, UsersPage.class);
        int usersCount = usersPage.getUsersCount();

        AdministrationPageDialog dialog = usersPage.addNewUser().fillCreateDialog(FIRST_USER_NAME, NEW_USER_NAME, FIRST_USER_PASSWORD, Role.USER);
        dialog.save(ALREADY_EXIST_MESSAGE);

        assertThat(usersPage.getUsersCount(), is(usersCount));
    }

    @Test
    public void adminSeeValidationErrorIfNoPassword() {
        UsersPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, UsersPage.class);

        AdministrationPageDialog dialog = usersPage.addNewUser().fillCreateDialog(NEW_USER_NAME, NEW_USER_NAME, null, Role.USER);
        dialog.save(VALIDATION_MESSAGE);
    }

    @Test
    public void adminSeeValidationErrorIfNoUserName() {
        UsersPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, UsersPage.class);

        AdministrationPageDialog dialog = usersPage.addNewUser().fillCreateDialog(null, NEW_USER_NAME, FIRST_USER_PASSWORD, Role.USER);
        dialog.save(VALIDATION_MESSAGE);
    }

    @Test
    public void adminCanCloseDialogWithoutSavingUser() {
        UsersPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, UsersPage.class);
        int usersCount = usersPage.getUsersCount();

        AdministrationPageDialog dialog = usersPage.addNewUser().fillCreateDialog(NEW_USER_NAME, null, FIRST_USER_PASSWORD, Role.USER);
        dialog.cancel();

        assertThat(usersPage.getUsersCount(), is(usersCount));
        assertFalse(usersPage.getUserByUserName(NEW_USER_NAME).isPresent());
    }

    @Test
    public void adminCanNotChangeUserNameWhileEditing() {
        UsersPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, UsersPage.class);

        AdministrationPageUser user = usersPage.getUserByUserName(FIRST_USER_NAME);

        assertTrue(user.edit().isUserNameFieldDisabled());
    }

    @Test
    public void adminCanEditUser() {
        UsersPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, UsersPage.class);
        int usersCount = usersPage.getUsersCount();

        AdministrationPageUser user = usersPage.getUserByUserName(FIRST_USER_NAME);
        AdministrationPageDialog dialog = user.edit().fillEditDialog(FIRST_USER_UPDATED_FULL_NAME, FIRST_USER_UPDATED_PASSWORD, Role.ADMIN);
        dialog.save(SUCCESS_MESSAGE);

        assertThat(usersPage.getUsersCount(), is(usersCount));
        assertTrue(user.getFullName().equals(FIRST_USER_UPDATED_FULL_NAME));
        assertTrue(user.getRole().equals(Role.ADMIN.toString()));

        goTo(LogoutPage.URL);
        LogoutPage logoutPage = new LogoutPage(getBrowser());
        logoutPage.logout();

        loginAndLoad(FIRST_USER_NAME, FIRST_USER_UPDATED_PASSWORD, UsersPage.class);
        assertTrue(usersPage.isAt());
    }

    @Test
    public void adminCanEditUserWithoutPasswordChange() {
        UsersPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, UsersPage.class);
        int usersCount = usersPage.getUsersCount();

        AdministrationPageUser user = usersPage.getUserByUserName(FIRST_USER_NAME);
        AdministrationPageDialog dialog = user.edit().fillEditDialog(FIRST_USER_UPDATED_FULL_NAME, EMPTY_PARAMETER, Role.ADMIN);
        dialog.save(SUCCESS_MESSAGE);

        assertThat(usersPage.getUsersCount(), is(usersCount));
        assertTrue(user.getFullName().equals(FIRST_USER_UPDATED_FULL_NAME));
        assertTrue(user.getRole().equals(Role.ADMIN.toString()));

        goTo(LogoutPage.URL);
        LogoutPage logoutPage = new LogoutPage(getBrowser());
        logoutPage.logout();

        loginAndLoad(FIRST_USER_NAME, FIRST_USER_PASSWORD, UsersPage.class);
        assertTrue(usersPage.isAt());
    }

    @Test
    public void adminSeeErrorWhileEditingNotExistingUser() {
        UsersPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, UsersPage.class);

        AdministrationPageUser user = usersPage.getUserByUserName(FIRST_USER_NAME);
        AdministrationPageDialog dialog = user.edit().fillEditDialog(FIRST_USER_UPDATED_FULL_NAME, EMPTY_PARAMETER, Role.ADMIN);
        user.delete();
        dialog.save(USER_NOT_FOUND);
    }

}