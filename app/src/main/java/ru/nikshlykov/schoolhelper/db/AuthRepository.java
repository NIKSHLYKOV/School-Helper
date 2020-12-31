package ru.nikshlykov.schoolhelper.db;

import android.app.Application;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;

import ru.nikshlykov.schoolhelper.App;
import ru.nikshlykov.schoolhelper.db.daos.UserDao;
import ru.nikshlykov.schoolhelper.db.entities.User;
import ru.nikshlykov.schoolhelper.ui.AuthActivity;

public class AuthRepository {

    private static AuthRepository instance;

    private static final String LOG_TAG = AuthRepository.class.getCanonicalName();

    private final UserDao userDao;

    private Application application;

    private AuthRepository(Application application) {
        userDao = AppDatabase.getInstance(application).userDao();
        this.application = application;
    }

    public static AuthRepository getInstance(Application application){
        if (instance == null){
            instance = new AuthRepository(application);
        }
        return instance;
    }

    public void signIn(String login, String password, OnSignInListener onSignInListener){
        User possibleUser = new User(0L, login, password, 0L);
        new SignInAsyncTask(userDao, onSignInListener).execute(possibleUser);
    }

    public interface OnSignInListener {
        void onSignIn(int signInStatus);
    }

    private class SignInAsyncTask extends AsyncTask<User, Void, Integer> {
        private UserDao userDao;
        private WeakReference<OnSignInListener> listener;

        private SignInAsyncTask(UserDao userDao, OnSignInListener listener) {
            this.userDao = userDao;
            this.listener = new WeakReference<>(listener);
        }

        @Override
        protected Integer doInBackground(User... users) {
            List<User> allUsers = userDao.getUsers();
            User possibleUser = users[0];
            boolean userExists = false;
            for (User user : allUsers) {
                if (possibleUser.nickname.equals(user.nickname) && possibleUser.password.equals(user.password)) {
                    userExists = true;
                    ((App)AuthRepository.this.application).setUser(user);
                    break;
                }
            }
            return userExists ? 1 : -1;
        }

        @Override
        protected void onPostExecute(Integer status) {
            super.onPostExecute(status);
            OnSignInListener listener = this.listener.get();
            if (listener != null) {
                listener.onSignIn(status);
            }
        }
    }
}
