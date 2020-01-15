package security.helpers

import com.jetbrains.python.psi.PyCallExpression
import com.jetbrains.python.psi.PyReferenceExpression
import com.jetbrains.python.psi.resolve.PyResolveContext

object QualifiedNames {
    val resolveContext: PyResolveContext = PyResolveContext.defaultContext()

    fun getQualifiedName(callExpression: PyCallExpression): String? {
        val markedCallees = callExpression.multiResolveCallee(resolveContext)
        if (markedCallees.isEmpty()) {
            val firstChild = callExpression.firstChild ?: return null
            val qualifiedName = (firstChild as PyReferenceExpression).asQualifiedName() ?: return null;
            return qualifiedName.toString()
        }
        else
            return markedCallees[0].element?.qualifiedName
    }
}