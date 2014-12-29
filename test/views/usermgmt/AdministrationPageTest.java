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
import static views.usermgmt.pages.UserCreateUpdateDialog.*;
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
        AdministrationPage page = new AdministrationPage(getBrowser());

        login(FIRST_USER_NAME, FIRST_USER_PASSWORD);
        goTo(AdministrationPage.URL);

        assertTrue(page.isAt());
        assertTrue(page.checkStatus(UNAUTHORIZED));
    }

    @Test
    public void adminCanOpenAdministrationPage() {
        AdministrationPage page = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);

        assertTrue(page.isAt());
    }

    @Test
    public void adminCanLogout() {
        LoginPage login = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class).logout();

        assertTrue(login.isAt());
        assertTrue(login.hasSuccess());
    }

    @Test
    public void adminCanSeeOwnFullName() {
        AdministrationPage page = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);

        assertThat(page.getDescriptionFullName(), is(ADMIN_FULL_NAME));
    }

    @Test
    public void adminCanDeleteUser() {
        AdministrationPage page = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);
        int usersCount = page.getUsersCount();

        UserRow<AdministrationPage> user = page.getUserByUserName(FIRST_USER_NAME);

        assertTrue(user.isDeleteButtonDisplayed());

        user.delete().submit();

        assertFalse(user.isPresent());
        assertThat(page.getUsersCount(), is(--usersCount));
    }

    @Test
    public void adminCanRejectDeletingUser() {
        AdministrationPage page = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);
        int usersCount = page.getUsersCount();

        UserRow<AdministrationPage> user = page.getUserByUserName(FIRST_USER_NAME);
        user.delete().cancel();

        assertTrue(user.isPresent());
        assertThat(page.getUsersCount(), is(usersCount));
    }

    @Test
    public void adminCanNotDeleteYourself() {
    	AdministrationPage page = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);
        int usersCount = page.getUsersCount();

        UserRow<AdministrationPage> user = page.getUserByUserName(ADMIN_USER_NAME);
        assertFalse(user.isDeleteButtonDisplayed());

        assertTrue(user.isPresent());
        assertThat(page.getUsersCount(), is(usersCount));
    }

    @Test
    public void adminCanAddNewUser() {
        AdministrationPage page = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);
        int usersCount = page.getUsersCount();

        UserCreateUpdateDialog<AdministrationPage> dialog = page.addNewUser().fillCreateDialog(NEW_USER_NAME, NEW_USER_NAME, FIRST_USER_PASSWORD, Role.USER);
        dialog.save(SUCCESS_MESSAGE);

        assertThat(page.getUsersCount(), is(++usersCount));
        assertTrue(page.getUserByUserName(NEW_USER_NAME).isPresent());
    }

    @Test
    public void adminCanAddNewUserWithoutSettingDirectlyFullName() {
        AdministrationPage page = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);
        int usersCount = page.getUsersCount();

        UserCreateUpdateDialog<AdministrationPage> dialog = page.addNewUser().fillCreateDialog(NEW_USER_NAME, EMPTY_PARAMETER, FIRST_USER_PASSWORD, Role.USER);
        dialog.save(SUCCESS_MESSAGE);
        UserRow<AdministrationPage> user = page.getUserByUserName(NEW_USER_NAME);

        assertThat(page.getUsersCount(), is(++usersCount));
        assertTrue(user.isPresent());
        assertTrue(user.getFullName().equals(NEW_USER_NAME));
    }

    @Test
    public void adminCanNotAddNewUserWithExistingName() {
        AdministrationPage page = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);
        int usersCount = page.getUsersCount();

        UserCreateUpdateDialog<AdministrationPage> dialog = page.addNewUser().fillCreateDialog(FIRST_USER_NAME, NEW_USER_NAME, FIRST_USER_PASSWORD, Role.USER);
        dialog.save(ALREADY_EXIST_MESSAGE);

        assertThat(page.getUsersCount(), is(usersCount));
    }

    @Test
    public void adminSeeValidationErrorIfNoPassword() {
        AdministrationPage page = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);

        UserCreateUpdateDialog<AdministrationPage> dialog = page.addNewUser().fillCreateDialog(NEW_USER_NAME, NEW_USER_NAME, EMPTY_PARAMETER, Role.USER);
        dialog.save(VALIDATION_MESSAGE);
    }

    @Test
    public void adminSeeValidationErrorIfNoUserName() {
        AdministrationPage page = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);

        UserCreateUpdateDialog<AdministrationPage> dialog = page.addNewUser().fillCreateDialog(EMPTY_PARAMETER, NEW_USER_NAME, FIRST_USER_PASSWORD, Role.USER);
        dialog.save(VALIDATION_MESSAGE);
    }

    @Test
    public void adminCanCloseDialogWithoutSavingUser() {
        AdministrationPage page = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);
        int usersCount = page.getUsersCount();

        UserCreateUpdateDialog<AdministrationPage> dialog = page.addNewUser().fillCreateDialog(NEW_USER_NAME, null, FIRST_USER_PASSWORD, Role.USER);
        dialog.cancel();

        assertThat(page.getUsersCount(), is(usersCount));
        assertFalse(page.getUserByUserName(NEW_USER_NAME).isPresent());
    }

    @Test
    public void adminCanNotChangeUserNameWhileEditing() {
        AdministrationPage page = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);

        UserRow<AdministrationPage> user = page.getUserByUserName(FIRST_USER_NAME);

        assertTrue(user.edit().isUserNameFieldDisabled());
    }

    @Test
    public void adminCanEditUser() {
        AdministrationPage page = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);
        int usersCount = page.getUsersCount();

        UserRow<AdministrationPage> user = page.getUserByUserName(FIRST_USER_NAME);
        UserCreateUpdateDialog<AdministrationPage> dialog = user.edit().fillEditDialog(FIRST_USER_UPDATED_FULL_NAME, FIRST_USER_UPDATED_PASSWORD, Role.ADMIN);
        dialog.save(SUCCESS_MESSAGE);

        assertThat(page.getUsersCount(), is(usersCount));
        assertTrue(user.getFullName().equals(FIRST_USER_UPDATED_FULL_NAME));
        assertTrue(user.getRole().equals(Role.ADMIN.toString()));

        goTo(LogoutPage.URL);
        LogoutPage logoutPage = new LogoutPage(getBrowser());
        logoutPage.logout();

        loginAndLoad(FIRST_USER_NAME, FIRST_USER_UPDATED_PASSWORD, AdministrationPage.class);
        assertTrue(page.isAt());
    }

    @Test
    public void adminCanEditUserWithoutPasswordChange() {
        AdministrationPage page = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);
        int usersCount = page.getUsersCount();

        UserRow<AdministrationPage> user = page.getUserByUserName(FIRST_USER_NAME);
        UserCreateUpdateDialog<AdministrationPage> dialog = user.edit().fillEditDialog(FIRST_USER_UPDATED_FULL_NAME, EMPTY_PARAMETER, Role.ADMIN);
        dialog.save(SUCCESS_MESSAGE);

        assertThat(page.getUsersCount(), is(usersCount));
        assertTrue(user.getFullName().equals(FIRST_USER_UPDATED_FULL_NAME));
        assertTrue(user.getRole().equals(Role.ADMIN.toString()));

        goTo(LogoutPage.URL);
        LogoutPage logoutPage = new LogoutPage(getBrowser());
        logoutPage.logout();

        loginAndLoad(FIRST_USER_NAME, FIRST_USER_PASSWORD, AdministrationPage.class);
        assertTrue(page.isAt());
    }

    @Test
    public void adminCanEditItsFullNameAndItBeUpdatedOnLogoutPanel() {
        AdministrationPage page = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);
        UserRow<AdministrationPage> user = page.getUserByUserName(ADMIN_USER_NAME);

        assertThat(page.getDescriptionFullName(), is(user.getFullName()));

        UserCreateUpdateDialog<AdministrationPage> dialog = user.edit().fillEditDialog(FIRST_USER_UPDATED_FULL_NAME, ADMIN_PASSWORD, Role.ADMIN);
        dialog.save(SUCCESS_MESSAGE);

        assertTrue(user.getFullName().equals(FIRST_USER_UPDATED_FULL_NAME));
        assertThat(page.getDescriptionFullName(), is(user.getFullName()));
    }

    @Test
    public void adminSeeErrorWhileEditingNotExistingUser() {
        AdministrationPage page = loginAndLoad(ADMIN_USER_NAME, ADMIN_PASSWORD, AdministrationPage.class);

        UserRow<AdministrationPage> user = page.getUserByUserName(FIRST_USER_NAME);
        UserCreateUpdateDialog<AdministrationPage> dialog = user.edit().fillEditDialog(FIRST_USER_UPDATED_FULL_NAME, EMPTY_PARAMETER, Role.ADMIN);
        user.delete().submit();
        dialog.save(USER_NOT_FOUND);
    }

}