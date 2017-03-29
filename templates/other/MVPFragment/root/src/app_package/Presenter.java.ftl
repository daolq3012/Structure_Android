package ${featureOut};

/**
 * Listens to user actions from the UI ({@link ${capFeatureName}Fragment}), retrieves the data and updates
 * the UI as required.
 */
final class ${capFeatureName}Presenter implements ${capFeatureName}Contract.Presenter {
	private static final String TAG = ${capFeatureName}Presenter.class.getName();

    private final ${capFeatureName}Contract.View mView;

    public ${capFeatureName}Presenter(${capFeatureName}Contract.View view) {
		mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
