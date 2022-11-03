package at.jku.cps.travart.plugin.ovm.configuration;

import at.jku.cps.travart.core.common.IConfigurable;
import at.jku.cps.travart.core.common.ISampler;
import at.jku.cps.travart.core.exception.NotSupportedVariabilityTypeException;
import at.jku.cps.travart.plugin.ovm.ovm.common.OvModelUtils;
import at.jku.cps.travart.plugin.ovm.ovm.model.IIdentifiable;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModel;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelElement;
import at.jku.cps.travart.plugin.ovm.ovm.model.impl.Identifiable;
import at.jku.cps.travart.plugin.ovm.ovm.transformation.DefaultOvModelTransformationProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class provides utils for sampling a model.
 *
 * @author johannstoebich
 */
public class OvModelSampler implements ISampler<IOvModel> {

    private static final long MAX_NUMBER_OF_VALID_CONFIGS = 1_000_000L;
    private final Set<Map<IConfigurable, Boolean>> validConfigs = new HashSet<>();
    private final Set<Map<IConfigurable, Boolean>> invalidConfigs = new HashSet<>();
    private IOvModel lastOvm;

    @Override
    public Set<Map<IConfigurable, Boolean>> sampleValidConfigurations(IOvModel ovm)
            throws NotSupportedVariabilityTypeException {
        if (this.lastOvm == null || this.lastOvm != ovm) {
            this.sample(ovm);
        }
        return this.validConfigs;
    }

    @Override
    public Set<Map<IConfigurable, Boolean>> sampleValidConfigurations(IOvModel ovm, long maxNumber)
            throws NotSupportedVariabilityTypeException {
        if (this.lastOvm == null || this.lastOvm != ovm) {
            this.sample(ovm);
        }
        return this.validConfigs.size() > maxNumber ? this.validConfigs.stream().limit(maxNumber).collect(Collectors.toSet())
                : this.validConfigs;
    }

    @Override
    public Set<Map<IConfigurable, Boolean>> sampleInvalidConfigurations(IOvModel ovm)
            throws NotSupportedVariabilityTypeException {
        if (this.lastOvm == null || this.lastOvm != ovm) {
            this.sample(ovm);
        }
        return this.invalidConfigs;
    }

    @Override
    public Set<Map<IConfigurable, Boolean>> sampleInvalidConfigurations(IOvModel ovm, long maxNumber)
            throws NotSupportedVariabilityTypeException {
        if (this.lastOvm == null || this.lastOvm != ovm) {
            this.sample(ovm);
        }
        return this.invalidConfigs.size() > maxNumber ? this.invalidConfigs.stream().limit(maxNumber).collect(Collectors.toSet())
                : this.invalidConfigs;
    }

    private void sample(IOvModel ovModel) {
        Set<IConfigurable> configurables = OvModelUtils.getIConfigurable(ovModel).keySet().stream()
                .filter(key -> !key.getName()
                        .contains(DefaultOvModelTransformationProperties.CONSTRAINT_VARIATION_POINT_PREFIX))
                .collect(Collectors.toSet());
        this.validConfigs.clear();
        this.invalidConfigs.clear();
        this.lastOvm = ovModel;
        long configcounter = 0;
        long maxConfigs = (long) Math.pow(2, configurables.size());
        for (long i = 0; i < maxConfigs; i++) {
            String bin = Long.toBinaryString(i);

            while (bin.length() < configurables.size()) {
                bin = "0" + bin;
            }
            this.resetConfiguration(configurables);
            this.applyConfiguration(configurables, bin);
            // no afterselection necessary, as all possible configurations are tested
            configcounter++;
            if (ovModel.isValid()) {
                Map<IConfigurable, Boolean> config = this.createConfiguration(ovModel);
                this.validConfigs.add(config);
                this.deriveInvalidConfigs(config);
                // cancel if max number of valid configurations is reached
                if (this.validConfigs.size() >= MAX_NUMBER_OF_VALID_CONFIGS) {
                    System.out.println("Generated " + configcounter
                            + " configurations to reach the maximal number of valid configurations");
                    return;
                }
            }
        }
    }

    private void deriveInvalidConfigs(Map<IConfigurable, Boolean> config) {
        Random rand = new Random();
        for (int count = 0; count < 10; count++) {
            int decisionSwitch = rand.nextInt(config.size());
            Map<IConfigurable, Boolean> invalid = new HashMap<>();
            int i = 0;
            for (Entry<IConfigurable, Boolean> entry : config.entrySet()) {
                IConfigurable key = entry.getKey();
                Boolean value = entry.getValue();
                if (i == decisionSwitch) {
                    key.setSelected(!key.isSelected());
                    value = !value;
                }
                invalid.put(key, value);
                i++;
            }
            // the randomly changed configuration my be valid so do not add it to the
            // invalid samples
            if (!this.verifySampleAs(this.lastOvm, invalid)) {
                this.invalidConfigs.add(invalid);
            }
        }
    }

    private Map<IConfigurable, Boolean> createConfiguration(IOvModel ovModel) {
        Map<IConfigurable, Boolean> config = OvModelUtils.getIConfigurable(ovModel);
        Iterator<Entry<IConfigurable, Boolean>> mapIterator = config.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Entry<IConfigurable, Boolean> entry = mapIterator.next();
            if (entry.getKey().getName()
                    .contains(DefaultOvModelTransformationProperties.CONSTRAINT_VARIATION_POINT_PREFIX)) {
                mapIterator.remove();
            }
        }
        return config;
    }

    /**
     * This method applys an binary configuration to the set of configuration.
     *
     * @param configurations
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
     * This method resets an binary configuration to the set of configuration.
     *
     * @param configurations
     */
    private void resetConfiguration(Set<IConfigurable> configurations) {
        for (IConfigurable configuration : configurations) {
            configuration.setSelected(false);
        }
    }

    /**
     * Verifys a a sample OVModel.
     *
     * @param ovm    The OVM which should be verified.
     * @param sample The samples which should be set.
     * @return
     */
    @Override
    public boolean verifySampleAs(IOvModel ovm, Map<IConfigurable, Boolean> sample) {
        List<IIdentifiable> samplesAsIdentifiable = new ArrayList<>();
        for (Entry<IConfigurable, Boolean> entry : sample.entrySet()) {
            if (entry.getValue()) {
                samplesAsIdentifiable.add(new Identifiable() {
                    {
                        this.setInternalId(0);
                        this.setName(entry.getKey().getName());
                    }
                });
            }
        }
        this.resetConfiguration(OvModelUtils.getIConfigurable(ovm).keySet());
        this.setSample(ovm, samplesAsIdentifiable);
        boolean valid = ovm.isValid();
        return valid;
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
            if (element != null) {
                if (!(element instanceof IConfigurable)) {
                    throw new IllegalArgumentException(
                            "The argument " + identifiable + " is not of type " + IConfigurable.class + ".");
                }
                ((IConfigurable) element).setSelected(true);
            }
        }
        ovm.afterSelection();
    }
}
