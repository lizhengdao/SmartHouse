/**
 *        http://www.june.com
 * Copyright © 2015 June.Co.Ltd. All Rights Reserved.
 */
package cn.com.zzwfang.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * @author Soo
 *
 */
public abstract class BaseFragment extends Fragment {
    
//    private View root;
    
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        if (root == null) {
//        	Log.i("--->", "BaseFragment onCreateView");
//        	container.removeAllViews();
//            root = onCreateRootView(inflater, container, savedInstanceState);
//        }
//        return root;
//    }
    
//    protected abstract View onCreateRootView(LayoutInflater inflater, ViewGroup container,
//            Bundle savedInstanceState);

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    
    public interface OnFragmentViewClickListener {
        void onFragmentViewClick(View view);
    }
}
