package ${featureOut};

import ${diOut}.BasePresenter;
import ${diOut}.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
interface ${capFeatureName}Contract {
	/**
	 * View.
	 */
    interface View extends BaseView {
    }
	
	/**
	 * Presenter.
	 */
    interface Presenter extends BasePresenter {
    }
}
