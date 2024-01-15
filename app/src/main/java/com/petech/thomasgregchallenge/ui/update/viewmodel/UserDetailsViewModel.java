package com.petech.thomasgregchallenge.ui.update.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.petech.thomasgregchallenge.data.entities.User;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserError;
import com.petech.thomasgregchallenge.ui.register.viewmodel.RegisterUserViewModelUtils;
import com.petech.thomasgregchallenge.ui.update.model.UserDetailsModel;
import com.petech.thomasgregchallenge.utils.AppUtils;

public class UserDetailsViewModel extends ViewModel {
    private final MutableLiveData<RegisterUserError> registerError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> userFound = new MutableLiveData<>();
    private final MutableLiveData<UserUpdateResult> userUpdated = new MutableLiveData<>();
    private final MutableLiveData<CurrentUserDetailsPage> currentPage = new MutableLiveData<>();
    private final UserDetailsModel userDetailsModel;

    public UserDetailsViewModel(UserDetailsModel userDetailsModel) {
        this.userDetailsModel = userDetailsModel;
    }

    public void findSelectedUser(int userId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    userDetailsModel.findUserById(userId);
                    userFound.postValue(true);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    registerError.postValue(RegisterUserError.UNKNOWN_ERROR);
                }
            }
        }).start();
    }

    public User getSelectedUser() {
        return userDetailsModel.getCurrentUser();
    }

    public void saveUserDetailsChanges(User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    updateUser(user);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    userUpdated.postValue(UserUpdateResult.UPDATE_ERROR);
                }
            }
        }).start();
    }

    public void changePassword(String password, String confirmPassword) {
        if (RegisterUserViewModelUtils.userPasswordIsNull(password, confirmPassword)) {
            registerError.postValue(RegisterUserError.USER_PASSWORD_EMPTY);
            return;
        }

        if (!password.equals(confirmPassword)) {
            registerError.postValue(RegisterUserError.PASSWORD_DONT_MATCHES);
            return;
        }

        if (!AppUtils.VALID_PASSWORD.matcher(password).matches()) {
            registerError.postValue(RegisterUserError.INVALID_PASSWORD);
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int rowsUpdated = userDetailsModel.changeUserPassword(password);

                    if (rowsUpdated > 0) {
                        userUpdated.postValue(UserUpdateResult.USER_UPDATED);
                    } else {
                        userUpdated.postValue(UserUpdateResult.UPDATE_ERROR);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                    userUpdated.postValue(UserUpdateResult.UPDATE_ERROR);
                }
            }
        }).start();
    }

    public void switchPageToChangePassword() {
        currentPage.postValue(CurrentUserDetailsPage.CHANGE_PASSWORD_PAGE);
    }

    private boolean validateInputUserData(User user) {
        if (UserDetailsViewModelUtils.userDataIsInvalid(user)) {
            registerError.postValue(RegisterUserError.DATA_FIELD_EMPTY);
            return false;
        }

        if (user.getName().length() < 30) {
            registerError.postValue(RegisterUserError.NAME_MUST_BE_COMPLETE);
            return false;
        }

        if (
                userDetailsModel.isUserNameExists(user.getUserName())
                        && !userDetailsModel.getCurrentUser().getUserName().equals(user.getUserName())
        ) {
            registerError.postValue(RegisterUserError.USER_NAME_ALREADY_EXISTS);
            return false;
        }

        if (!AppUtils.VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail()).matches()) {
            registerError.postValue(RegisterUserError.EMAIL_INVALID);
            return false;
        }

        return true;
    }

    private void updateUser(User user) {
        if (user.equals(userDetailsModel.getCurrentUser())) {
            userUpdated.postValue(UserUpdateResult.USER_IS_SAME);
        }

        if (UserDetailsViewModelUtils.validationsToUpdateUser(
                user,
                validateInputUserData(user),
                registerError
        )) {
            int rowUpdated = userDetailsModel.updateCurrentUser(user);
            if (rowUpdated > 0) {
                userUpdated.postValue(UserUpdateResult.USER_UPDATED);
            } else {
                userUpdated.postValue(UserUpdateResult.UPDATE_ERROR);
            }
        }
    }


    public LiveData<RegisterUserError> getRegisterError() {
        return registerError;
    }

    public LiveData<UserUpdateResult> getUserUpdated() {
        return userUpdated;
    }

    public LiveData<Boolean> getUserFound() {
        return userFound;
    }

    public LiveData<CurrentUserDetailsPage> getCurrentPage() {
        return currentPage;
    }
}
