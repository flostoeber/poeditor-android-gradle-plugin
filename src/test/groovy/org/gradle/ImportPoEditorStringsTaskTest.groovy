package org.gradle

import com.bq.gradle.ImportPoEditorStringsTask
import com.bq.gradle.PoEditorPluginExtension
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

/**
 * ImportPoEditorStrings task test.
 *
 * Created by imartinez on 11/1/16.
 */
class ImportPoEditorStringsTaskTest {
    @Test
    public void canAddTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        def task = project.task('importPoEditorStrings', type: ImportPoEditorStringsTask)
        assertTrue(task instanceof ImportPoEditorStringsTask)
    }

    @Test(expected=IllegalStateException.class)
    public void testExecutedWithoutNeededExtensionThrowsException() {
        Project project = ProjectBuilder.builder().build()
        def task = project.task('importPoEditorStrings', type: ImportPoEditorStringsTask)
        assertTrue(task instanceof ImportPoEditorStringsTask)

        // No extension is set

        // Test this throws IllegalStateException
        ((ImportPoEditorStringsTask)task).importPoEditorStrings()
    }

    @Test(expected=IllegalStateException.class)
    public void testExecutedWithEmptyExtensionThrowsException() {
        Project project = ProjectBuilder.builder().build()
        def task = project.task('importPoEditorStrings', type: ImportPoEditorStringsTask)
        assertTrue(task instanceof ImportPoEditorStringsTask)

        // Set empty extension
        def emptyExtension = new PoEditorPluginExtension()
        project.extensions.add("poEditorPlugin", emptyExtension)

        // Test this throws IllegalStateException
        ((ImportPoEditorStringsTask)task).importPoEditorStrings()
    }

    @Test(expected=IllegalStateException.class)
    public void testExecutedWithInvalidAPICredentialsThrowsException() {
        Project project = ProjectBuilder.builder().build()
        def task = project.task('importPoEditorStrings', type: ImportPoEditorStringsTask)
        assertTrue(task instanceof ImportPoEditorStringsTask)

        // Set empty extension
        def invalidAPICredentialsExtension = new PoEditorPluginExtension()
        invalidAPICredentialsExtension.project_id = "invalid_project_id"
        invalidAPICredentialsExtension.api_token = "invalid_api_oken"
        invalidAPICredentialsExtension.default_lang = "fake_lang"
        invalidAPICredentialsExtension.res_dir_path = "fake_path"

        project.extensions.add("poEditorPlugin", invalidAPICredentialsExtension)

        // Test this throws IllegalStateException
        ((ImportPoEditorStringsTask)task).importPoEditorStrings()
    }

    @Test
    public void testPostProcessIncomingXMLStringLineEnd() throws Exception {
        Project project = ProjectBuilder.builder().build()
        def task = project.task('importPoEditorStrings', type: ImportPoEditorStringsTask)

        // Test \n is maintained
        assertEquals('Hello my friend\nHow are you?.',
                ((ImportPoEditorStringsTask)task).postProcessIncomingXMLString('Hello my friend\nHow are you?.'))
    }

    @Test
    public void testPostProcessIncomingXMLStringPlaceHolders() throws Exception {
        Project project = ProjectBuilder.builder().build()
        def task = project.task('importPoEditorStrings', type: ImportPoEditorStringsTask)

        // Test placeholders are translated to Android format
        assertEquals('Hello %1$s.',
                ((ImportPoEditorStringsTask)task).postProcessIncomingXMLString('Hello %1$s.'))
        assertEquals('Hello %1$s. How are you %1$s',
                ((ImportPoEditorStringsTask)task).postProcessIncomingXMLString('Hello %1$s. How are you %1$s'))
        assertEquals('Hello %1$s. This is your score %2$s',
                ((ImportPoEditorStringsTask)task).postProcessIncomingXMLString('Hello %1$s. This is your score %2$s'))
    }

    @Test
    public void testPostProcessIncomingXMLStringHTML() throws Exception {
        Project project = ProjectBuilder.builder().build()
        def task = project.task('importPoEditorStrings', type: ImportPoEditorStringsTask)

        // Test HTML tags are fixed
        assertEquals('Hello <b>%1$s</b>.',
                ((ImportPoEditorStringsTask)task).postProcessIncomingXMLString('Hello &lt;b&gt;%1$s&lt;/b&gt;.'))
    }

    @Test
    public void testPostProcessIncomingXMLStringWithComplexXML() throws Exception {
        Project project = ProjectBuilder.builder().build()
        def task = project.task('importPoEditorStrings', type: ImportPoEditorStringsTask)
        assertTrue(task instanceof ImportPoEditorStringsTask)

        // Test complete XML
        assertEquals('<resources>\n' +
                '  <string name="general_link_showAll">\n' +
                '    "Ver todo %1$s"\n' +
                '  </string>\n' +
                '  <string name="general_button_goTop">\n' +
                '    "Ir arriba"\n' +
                '  </string>\n' +
                '  <string name="general_button_back_web">\n' +
                '    "Ir arriba %1$s usuario %2$s"\n' +
                '  </string>\n' +
                ' </resources>',
        ((ImportPoEditorStringsTask)task).postProcessIncomingXMLString('<resources>\n' +
                '  <string name="general_link_showAll">\n' +
                '    "Ver todo %1$s"\n' +
                '  </string>\n' +
                '  <string name="general_button_goTop">\n' +
                '    "Ir arriba"\n' +
                '  </string>\n' +
                '  <string name="general_button_back_web">\n' +
                '    "Ir arriba %1$s usuario %2$s"\n' +
                '  </string>\n' +
                ' </resources>'))
    }

    @Test
    public void testCreateValuesModifierFromLangCodeWithNormalLangCode() throws Exception {
        Project project = ProjectBuilder.builder().build()
        def task = project.task('importPoEditorStrings', type: ImportPoEditorStringsTask)

        assertEquals('es',
                ((ImportPoEditorStringsTask)task).createValuesModifierFromLangCode('es'))
    }

    @Test
    public void testCreateValuesModifierFromLangCodeWithSpecializedLangCode() throws Exception {
        Project project = ProjectBuilder.builder().build()
        def task = project.task('importPoEditorStrings', type: ImportPoEditorStringsTask)

        assertEquals('es-rMX',
                ((ImportPoEditorStringsTask)task).createValuesModifierFromLangCode('es-mx'))
    }
}