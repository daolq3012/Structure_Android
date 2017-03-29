<?xml version="1.0"?>
<globals>
	<!-- Common -->
    <global id="diOut" value="${packageName}.screen" />
    <global id="scopeOut" value="${packageName}" />
    <global id="appClassName" value="MainApplication" />
    <global id="appTheme" value="AppTheme" />

    <!-- Feature folder -->
    <global id="manifestOut" value="${manifestDir}" />
    <global id="srcOut" value="${srcDir}/${slashedPackageName(packageName)}" />
    <global id="resOut" value="${resDir}" />
    <global id="featureOut" value="${packageName}.screen.${featureName}" />
    <global id="capFeatureName" value="${featureName?cap_first}" />
    <global id="parentActivityClass" value="" />
    <global id="featureFolder" value="./app/src/main/java/${slashedPackageName(packageName)}/screen/${featureName}" />

    <#include "../common/common_globals.xml.ftl" />
</globals>
