package ${featureOut};

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ${packageName}.R;
import ${packageName}.screen.BaseFragment;

/**
 * ${capFeatureName} Screen.
 */
public class ${capFeatureName}Fragment extends BaseFragment implements ${capFeatureName}Contract.View {

    ${capFeatureName}Contract.Presenter  mPresenter;
	
	public static ${capFeatureName}Fragment newInstance() {
        return new ${capFeatureName}Fragment();
    }
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		
        mPresenter = new ${capFeatureName}Presenter(this);
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_${featureName?lower_case}, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
        super.onStop();
    }
}
