package views.usermgmt;

import models.usermgmt.Role;

import org.junit.Before;
import org.junit.Test;

import commons.ui.AbstractUITest;
import usermgmt.YAML;
import views.usermgmt.pages.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static usermgmt.Parameters.*;
import static views.usermgmt.pages.AdministrationPageDialog.*;
import static commons.ui.pages.PageStatus.*;

public class AdministrationPageTest extends AbstractUITest{

    @Override
    @Before
    public void setUp(){
        super.setUp();
        YAML.GENERAL_USERS.load();
    }

    @Test
    public void onlyAdminHasAccessToAdministrationPage() {
        AdministrationPage usersPage = new AdministrationPage(getBrowser());

        login(FIRST_USER_NAME, FIRST_USER_PASSWORD);
        goTo(AdministrationPage.URL);

        assertTrue(usersPage.isAt());
        assertTrue(usersPage.checkStatus(UNAUTHORIZED));
    }

    @Test
    public void adminCanOpenAdministrationPage() {
        AdministrationPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);

        assertTrue(usersPage.isAt());
    }

    @Test
    public void adminCanLogout() {
        LoginPage login = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class).logout();

        assertTrue(login.isAt());
        assertTrue(login.hasSuccess());
    }

    @Test
    public void adminCanSeeOwnFullName() {
        AdministrationPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);

        assertThat(usersPage.getDescriptionFullName(), is(ADMIN_FULL_NAME));
    }

    @Test
    public void adminCanDeleteUser() {
        AdministrationPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);
        int usersCount = usersPage.getUsersCount();

        AdministrationPageUser user = usersPage.getUserByUserName(FIRST_USER_NAME);

        assertTrue(user.isDeleteButtonDisplayed());

        user.delete().submit();

        assertFalse(user.isPresent());
        assertThat(usersPage.getUsersCount(), is(--usersCount));
    }

    @Test
    public void adminCanRejectDeletingUser() {
        UsersPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, UsersPage.class);
        int usersCount = usersPage.getUsersCount();

        AdministrationPageUser user = usersPage.getUserByUserName(FIRST_USER_NAME);
        user.delete().cancel();

        assertTrue(user.isPresent());
        assertThat(usersPage.getUsersCount(), is(usersCount));
    }

    @Test
    public void adminCanNotDeleteYourself() {
        UsersPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, UsersPage.class);
        int usersCount = usersPage.getUsersCount();

        AdministrationPageUser user = usersPage.getUserByUserName(ADMIN_USER_NAME);
        assertFalse(user.isDeleteButtonDisplayed());

        assertTrue(user.isPresent());
        assertThat(usersPage.getUsersCount(), is(usersCount));
    }

    @Test
    public void adminCanAddNewUser() {
        AdministrationPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);
        int usersCount = usersPage.getUsersCount();

        AdministrationPageDialog dialog = usersPage.addNewUser().fillCreateDialog(NEW_USER_NAME, NEW_USER_NAME, FIRST_USER_PASSWORD, Role.USER);
        dialog.save(SUCCESS_MESSAGE);

        assertThat(usersPage.getUsersCount(), is(++usersCount));
        assertTrue(usersPage.getUserByUserName(NEW_USER_NAME).isPresent());
    }

    @Test
    public void adminCanAddNewUserWithoutSettingDirectlyFullName() {
        AdministrationPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);
        int usersCount = usersPage.getUsersCount();

        AdministrationPageDialog dialog = usersPage.addNewUser().fillCreateDialog(NEW_USER_NAME, EMPTY_PARAMETER, FIRST_USER_PASSWORD, Role.USER);
        dialog.save(SUCCESS_MESSAGE);
        AdministrationPageUser user = usersPage.getUserByUserName(NEW_USER_NAME);

        assertThat(usersPage.getUsersCount(), is(++usersCount));
        assertTrue(user.isPresent());
        assertTrue(user.getFullName().equals(NEW_USER_NAME));
    }

    @Test
    public void adminCanNotAddNewUserWithExistingName() {
        AdministrationPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);
        int usersCount = usersPage.getUsersCount();

        AdministrationPageDialog dialog = usersPage.addNewUser().fillCreateDialog(FIRST_USER_NAME, NEW_USER_NAME, FIRST_USER_PASSWORD, Role.USER);
        dialog.save(ALREADY_EXIST_MESSAGE);

        assertThat(usersPage.getUsersCount(), is(usersCount));
    }

    @Test
    public void adminSeeValidationErrorIfNoPassword() {
        AdministrationPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);

        AdministrationPageDialog dialog = usersPage.addNewUser().fillCreateDialog(NEW_USER_NAME, NEW_USER_NAME, EMPTY_PARAMETER, Role.USER);
        dialog.save(VALIDATION_MESSAGE);
    }

    @Test
    public void adminSeeValidationErrorIfNoUserName() {
        AdministrationPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);

        AdministrationPageDialog dialog = usersPage.addNewUser().fillCreateDialog(EMPTY_PARAMETER, NEW_USER_NAME, FIRST_USER_PASSWORD, Role.USER);
        dialog.save(VALIDATION_MESSAGE);
    }

    @Test
    public void adminCanCloseDialogWithoutSavingUser() {
        AdministrationPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);
        int usersCount = usersPage.getUsersCount();

        AdministrationPageDialog dialog = usersPage.addNewUser().fillCreateDialog(NEW_USER_NAME, null, FIRST_USER_PASSWORD, Role.USER);
        dialog.cancel();

        assertThat(usersPage.getUsersCount(), is(usersCount));
        assertFalse(usersPage.getUserByUserName(NEW_USER_NAME).isPresent());
    }

    @Test
    public void adminCanNotChangeUserNameWhileEditing() {
        AdministrationPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);

        AdministrationPageUser user = usersPage.getUserByUserName(FIRST_USER_NAME);

        assertTrue(user.edit().isUserNameFieldDisabled());
    }

    @Test
    public void adminCanEditUser() {
        AdministrationPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);
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

        loginAndLoad(FIRST_USER_NAME, FIRST_USER_UPDATED_PASSWORD, AdministrationPage.class);
        assertTrue(usersPage.isAt());
    }

    @Test
    public void adminCanEditUserWithoutPasswordChange() {
        AdministrationPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);
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

        loginAndLoad(FIRST_USER_NAME, FIRST_USER_PASSWORD, AdministrationPage.class);
        assertTrue(usersPage.isAt());
    }

    @Test
    public void adminCanEditItsFullNameAndItBeUpdatedOnLogoutPanel() {
        UsersPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, UsersPage.class);
        AdministrationPageUser user = usersPage.getUserByUserName(ADMIN_USER_NAME);

        assertThat(usersPage.getDescriptionFullName(), is(user.getFullName()));

        AdministrationPageDialog dialog = user.edit().fillEditDialog(FIRST_USER_UPDATED_FULL_NAME, ADMIN_PASSWORD, Role.ADMIN);
        dialog.save(SUCCESS_MESSAGE);

        assertTrue(user.getFullName().equals(FIRST_USER_UPDATED_FULL_NAME));
        assertThat(usersPage.getDescriptionFullName(), is(user.getFullName()));
    }

    @Test
    public void adminSeeErrorWhileEditingNotExistingUser() {
        AdministrationPage usersPage = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);

        AdministrationPageUser user = usersPage.getUserByUserName(FIRST_USER_NAME);
        AdministrationPageDialog dialog = user.edit().fillEditDialog(FIRST_USER_UPDATED_FULL_NAME, EMPTY_PARAMETER, Role.ADMIN);
        user.delete().submit();
        dialog.save(USER_NOT_FOUND);
    }

}