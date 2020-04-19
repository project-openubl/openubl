package org.openubl.quarkus.bouncycastle.deployment;

import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;
import io.quarkus.deployment.builditem.nativeimage.RuntimeInitializedClassBuildItem;

class BouncycastleProcessor {

    private static final String FEATURE = "bouncycastle";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void reflective(BuildProducer<ReflectiveClassBuildItem> reflectiveClass) {
        reflectiveClass.produce(
                new ReflectiveClassBuildItem(
                        false,
                        false,
                        "org.bouncycastle.jcajce.provider.asymmetric.rsa.KeyFactorySpi",
                        "org.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory"));
    }

    @BuildStep
    void configure(BuildProducer<RuntimeInitializedClassBuildItem> runtimeClasses) {
        runtimeClasses.produce(new RuntimeInitializedClassBuildItem(org.bouncycastle.crypto.prng.SP800SecureRandom.class.getCanonicalName()));
        runtimeClasses.produce(new RuntimeInitializedClassBuildItem("org.bouncycastle.jcajce.provider.drbg.DRBG$NonceAndIV"));
        runtimeClasses.produce(new RuntimeInitializedClassBuildItem("org.bouncycastle.jcajce.provider.drbg.DRBG$Default"));
    }
}
