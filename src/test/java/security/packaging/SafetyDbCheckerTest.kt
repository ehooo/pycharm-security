package security.packaging

import com.jetbrains.python.packaging.PyPackage
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.StringReader

internal class SafetyDbCheckerTest {
    lateinit var instance: SafetyDbChecker

    @BeforeEach
    fun setUp() {
        val testLookupData = """
            {
             "test_package": [],
             "aiocouchdb": [
                "<0.6.0"
                ]
            }
        """.trimIndent()
        val testData = """
            {
             "aiocouchdb": [
                 {
                    "advisory": "aiocouchdb 0.6.0 now correctly set members for database security.",
                    "cve": null,
                    "id": "pyup.io-25612",
                    "specs": [
                        "<0.6.0"
                    ],
                    "v": "<0.6.0"
                }
                ]
            }
        """.trimIndent()
        val lookupReader = StringReader(testLookupData)
        val databaseReader = StringReader(testData)
        this.instance = SafetyDbChecker(lookupReader = lookupReader, databaseReader = databaseReader)
    }

    @Test
    fun testVulnPackageHasMatch() {
        val testPackage = mock<PyPackage> {
            on { name } doReturn "aiocouchdb"
            on { version } doReturn "0.1.0"
        }
        assertTrue(instance.hasMatch(testPackage))
    }

    @Test
    fun testMissingPackageDoesNotHaveMatch() {
        val testPackage = mock<PyPackage> {
            on { name } doReturn "madeup"
            on { version } doReturn "0.1.0"
        }
        assertFalse(instance.hasMatch(testPackage))
    }

    @Test
    fun getMatches() {
    }
}