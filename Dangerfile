# github comment settings
github.dismiss_out_of_range_messages

# for PR
if github.pr_title.include?('[WIP]') || github.pr_labels.include?('WIP')
  warn('PR is classed as Work in Progress')
end

# Warn when there is a big PR
warn('a large PR') if git.lines_of_code > 300

checkstyle_format.base_path = Dir.pwd

# checkstyle
checkstyle_format.report 'app/build/reports/checkstyle/checkstyle.xml'

# Findbugs
require 'findbugs_translate_checkstyle_format'
findbugs_xml = ::FindbugsTranslateCheckstyleFormat::Script.translate(File.read('app/build/reports/findbugs/findbugs.xml'))
checkstyle_format.report_by_text findbugs_xml

# PMD
require 'pmd_translate_checkstyle_format'
pmd_xml = ::PmdTranslateCheckstyleFormat::Script.translate(File.read('app/build/reports/pmd/pmd.xml'))
checkstyle_format.report_by_text pmd_xml

# PMD-CPD
require 'pmd_translate_checkstyle_format'
pmd_cpd_xml = ::PmdTranslateCheckstyleFormat::Script.translate_cpd(File.read('app/build/reports/pmd/cpd.xml'))
checkstyle_format.report_by_text pmd_cpd_xml

# # Android Lint
require 'android_lint_translate_checkstyle_format'
android_lint_xml = ::AndroidLintTranslateCheckstyleFormat::Script.translate(File.read('app/build/reports/lint-results.xml'))
checkstyle_format.report_by_text android_lint_xml
