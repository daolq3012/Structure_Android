<?xml version="1.0"?>
<recipe>

	<merge from="root/src/AndroidManifest.xml.ftl"
			 to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />

	<instantiate from="root/src/app_package/Activity.java.ftl"
					to="${featureFolder}/${capFeatureName}Activity.java" />

	<instantiate from="root/res/layout/layout.xml.ftl"
					 to="${escapeXmlAttribute(resOut)}/layout/activity_${featureName?lower_case}.xml" />

	<open file="${featureFolder}/${capFeatureName}Activity.java" />

	<instantiate from="root/src/app_package/Contract.java.ftl"
                   to="${featureFolder}/${capFeatureName}Contract.java" />
	
	<instantiate from="root/src/app_package/Presenter.java.ftl"
				   to="${featureFolder}/${capFeatureName}Presenter.java" />     				   
</recipe>
