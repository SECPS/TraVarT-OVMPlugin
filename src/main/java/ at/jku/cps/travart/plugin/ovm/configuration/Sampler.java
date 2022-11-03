import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class represents sume utils for sampeling a model.
 *
 * @author johannstoebich
 */
public class Sampler {

    private final int MAX_NUMBER_OF_VALID_CONFIGS = 1000;

    private final List<Map<IConfigurable, Boolean>> validConfigs = new ArrayList<Map<IConfigurable, Boolean>>();

    public List<Map<IConfigurable, Boolean>> getValidConfigs() {
        return this.validConfigs;
    }

    /**
     * This method iterates over all possible configurations of a model and stores them in the validConfigurations set if they are valid.
     *
     * @param ovModel the model which should be verified.
     */
    public void createConfigurations(IOvModel ovModel) {

        Set<IConfigurable> configurables = OvModelUtils.getIConfigurable(ovModel).keySet();
        this.validConfigs.clear();

        for (int i = 0; i < Math.pow(2, configurables.size()); i++) {
            String bin = Integer.toBinaryString(i);

            while (bin.length() < configurables.size()) {
                bin = bin + "0";
            }
            System.out.println("Check configuratoin " + bin + ".");
            this.applyConfiguration(configurables, bin);
            ovModel.afterSelection();

            if (ovModel.isValid()) {
                this.validConfigs.add(OvModelUtils.getIConfigurable(ovModel));

                // cancel if max number of valid configurations is reached
                if (this.validConfigs.size() >= this.MAX_NUMBER_OF_VALID_CONFIGS) {
                    System.out.println("Configurations found: " + this.validConfigs.size());
                    return;
                }
            }
        }
    }

    /**
     * This method applys an binary configuration to the set of configuration.
     *
     * @param configuration
     * @param bin
     */
    private void applyConfiguration(Set<IConfigurable> configurations, String bin) {
        if (configurations.size() != bin.length()) {
            throw new IllegalArgumentException("Lenghts must be of the same size.");
        }

        int i = 0;
        for (IConfigurable configuration : configurations) {
            configuration.setSelected(bin.charAt(i) == '1');
            i++;
        }
    }

    /**
     * Verifys a a sample OVModel.
     *
     * @param ovm       The OVM which should be verified.
     * @param samples   The samples which should be set.
     * @param roundtrip true whenever a roundtrip should be done.
     * @return
     */
    public boolean verifySampleAs(IOvModel ovm, List<String> samples) {
        List<IIdentifiable> samplesAsIdentifiable = new ArrayList<IIdentifiable>();
        for (String sample : samples) {
            samplesAsIdentifiable.add(new Identifiable() {

                {
                    this.setInternalId(0);
                    this.setName(sample);
                }
            });
        }
        this.setSample(ovm, samplesAsIdentifiable);
        return ovm.isValid();
    }

    /**
     * This method sets a sample of identifyables to a model.
     *
     * @param ovm           the ovModel
     * @param identifiables
     */
    private void setSample(IOvModel ovm, List<IIdentifiable> identifiables) {
        for (IIdentifiable identifiable : identifiables) {
            IOvModelElement element = ovm.getElement(identifiable);
            if (element == null) {
                throw new IllegalArgumentException("The argument " + identifiable + " has not been found.");
            }
            if (!(element instanceof IConfigurable)) {
                throw new IllegalArgumentException("The argument " + identifiable + " is not of type " + IConfigurable.class + ".");
            }
            ((IConfigurable) element).setSelected(true);
        }
        ovm.afterSelection();
    }
}
