package ${featureOut};

import android.os.Bundle;
import ${packageName}.R;
import ${packageName}.screen.BaseActivity;

/**
 * ${capFeatureName} Screen.
 */
public class ${capFeatureName}Activity extends BaseActivity implements ${capFeatureName}Contract.View {

    ${capFeatureName}Contract.Presenter  mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_${featureName?lower_case});
		
	mPresenter = new ${capFeatureName}Presenter();
	mPresenter.setView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        mPresenter.onStop();
        super.onStop();
    }
}
