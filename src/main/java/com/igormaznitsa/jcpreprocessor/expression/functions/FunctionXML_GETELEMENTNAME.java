package com.igormaznitsa.jcpreprocessor.expression.functions;

import com.igormaznitsa.jcpreprocessor.expression.Expression;
import com.igormaznitsa.jcpreprocessor.expression.Value;
import java.io.File;
import org.w3c.dom.Element;

public final class FunctionXML_GETELEMENTNAME extends AbstractXMLFunction {

    @Override
    public String getName() {
        return "xml_getelementname";
    }

    public void execute(Expression _stack, int _index) {
        if (!_stack.isThereOneValueBefore(_index)) {
            throw new IllegalStateException("Operation XML_GETELEMENTNAME needs an operand");
        }

        Value _val0 = (Value) _stack.getItemAtPosition(_index - 1);
        _index--;
        _stack.removeItemAt(_index);

        switch (_val0.getType()) {
            case INT: {
                long l_elementIndex = ((Long) _val0.getValue()).longValue();

                Element p_element = getXmlElementForIndex((int) l_elementIndex);
                String s_name = p_element.getNodeName();

                if (s_name == null) {
                    s_name = "";
                }

                _stack.setItemAtPosition(_index, new Value((Object) s_name));
            }
            ;
            break;
            default:
                throw new IllegalArgumentException("Function XML_GETELEMENTNAME processes only the INTEGER types");
        }

    }
    
        @Override
    public int getArity() {
        return 1;
    }
    

}
