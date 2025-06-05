package dev.donmanuel.solidandroid.presentation.login;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * Adaptador para el ViewPager2 que maneja los fragmentos de login y registro
 * (Principio de Responsabilidad Única - solo maneja la navegación entre fragmentos)
 */
public class LoginPagerAdapter extends FragmentStateAdapter {

    private static final int NUM_PAGES = 2;
    private static final int LOGIN_PAGE = 0;
    private static final int REGISTER_PAGE = 1;

    public LoginPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case LOGIN_PAGE:
                return new LoginFragment();
            case REGISTER_PAGE:
                return new RegisterFragment();
            default:
                return new LoginFragment();
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
