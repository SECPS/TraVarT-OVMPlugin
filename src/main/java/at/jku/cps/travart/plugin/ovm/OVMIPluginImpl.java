package at.jku.cps.travart.plugin.ovm;

import at.jku.cps.travart.core.common.IModelTransformer;
import at.jku.cps.travart.core.common.IPlugin;
import at.jku.cps.travart.core.common.IReader;
import at.jku.cps.travart.core.common.IWriter;
import at.jku.cps.travart.plugin.ovm.ovm.io.OvModelReader;
import at.jku.cps.travart.plugin.ovm.ovm.io.OvModelWriter;
import at.jku.cps.travart.plugin.ovm.ovm.transformation.ModelTransformerImpl;
import org.pf4j.Extension;

import java.util.List;

@Extension
public class OVMIPluginImpl implements IPlugin {
    @Override
    public IModelTransformer getTransformer() {
        return new ModelTransformerImpl();
    }

    @Override
    public IReader getReader() {
        return new OvModelReader();
    }

    @Override
    public IWriter getWriter() {
        return new OvModelWriter();
    }

    @Override
    public String getName() {
        return "OVM";
    }

    @Override
    public String getVersion() {
        return "0.0.1";
    }

    @Override
    public String getId() {
        return "ovm-plugin";
    }

    @Override
    public List<String> getSupportedFileExtensions() {
        return List.of(".ovm");
    }
}
