<?xml version="1.0"?>
<recipe>

	<instantiate from="root/src/app_package/Fragment.java.ftl"
					to="${featureFolder}/${capFeatureName}Fragment.java" />

	<instantiate from="root/res/layout/layout.xml.ftl"
					 to="${escapeXmlAttribute(resOut)}/layout/fragment_${featureName?lower_case}.xml" />

	<open file="${featureFolder}/${capFeatureName}Fragment.java" />

	<instantiate from="root/src/app_package/Contract.java.ftl"
                   to="${featureFolder}/${capFeatureName}Contract.java" />
	
	<instantiate from="root/src/app_package/Presenter.java.ftl"
				   to="${featureFolder}/${capFeatureName}Presenter.java" />     				   
</recipe>
