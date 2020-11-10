package com.android.base.fragment;

import com.android.base.activity.CoreActivity;
import androidx.fragment.app.Fragment;


/**
 * CoreFragment：
 * 声明跟系统相关的方法
 */
public class CoreFragment extends Fragment {

    private static final String TAG = CoreFragment.class.getSimpleName();

    private CoreActivity getBaseActivity() {
        if (getActivity() instanceof CoreActivity)
            return (CoreActivity) getActivity();
        else
            throw new ClassCastException("activity must extends CoreActivity");
    }

    public void addFragment(Fragment frg) {
        getBaseActivity().addFragment(frg, null);
    }

    public void addFragmentToStack(Fragment frg) {
        getBaseActivity().addFragmentToStack(frg, null);
    }

    public void replaceFragment(Fragment frg) {
        getBaseActivity().replaceFragment(frg, null);
    }

    public void replaceFragmentToStack(Fragment fragment) {
        getBaseActivity().replaceFragmentToStack(fragment, null);
    }

    public void addFragment(Fragment frg, Fragment sourceFragment) {
        getBaseActivity().addFragment(frg, sourceFragment);
    }

    public void addFragmentToStack(Fragment frg, Fragment sourceFragment) {
        getBaseActivity().addFragmentToStack(frg, sourceFragment);

    }

    public void replaceFragment(Fragment frg, Fragment sourceFragment) {
        getBaseActivity().replaceFragment(frg, sourceFragment);

    }

    public void replaceFragmentToStack(Fragment frg, Fragment sourceFragment) {
        getBaseActivity().replaceFragmentToStack(frg, sourceFragment);
    }

    public void popFragment() {
        getBaseActivity().popFragment();
    }

    public void popFragment(Class fragmentClazz, int type) {
        getBaseActivity().popFragment(fragmentClazz, type);
    }

    public void popBackStackImmediate() {
        getBaseActivity().popBackStackImmediate();
    }

    public void popBackStackImmediate(Class fragmentClazz, int type) {
        getBaseActivity().popBackStackImmediate(fragmentClazz, type);
    }

    /**
     * 如果对返回事件进行了处理就返回TRUE,如果不做处理就返回FALSE,让上层进行处理。
     */
    public boolean onBackPressed() {
        return false;
    }

}
