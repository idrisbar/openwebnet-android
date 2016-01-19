package com.github.openwebnet.service;

import com.github.openwebnet.BuildConfig;
import com.github.openwebnet.component.ApplicationComponentTest;
import com.github.openwebnet.component.DaggerApplicationComponentTest;
import com.github.openwebnet.component.Injector;
import com.github.openwebnet.component.module.ApplicationContextModuleTest;
import com.github.openwebnet.component.module.DomoticModuleTest;
import com.github.openwebnet.component.module.RepositoryModuleTest;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import javax.inject.Inject;

import rx.Observable;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
@PrepareForTest({Injector.class})
public class CommonServiceTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @Inject
    CommonService commonService;

    @Inject
    PreferenceService preferenceService;

    @Inject
    EnvironmentService environmentService;

    @Before
    public void setupDagger() {
        ApplicationComponentTest applicationComponentTest = DaggerApplicationComponentTest.builder()
            .applicationContextModuleTest(new ApplicationContextModuleTest())
            .domoticModuleTest(new DomoticModuleTest())
            .repositoryModuleTest(new RepositoryModuleTest(true))
            .build();

        PowerMockito.mockStatic(Injector.class);
        PowerMockito.when(Injector.getApplicationComponent()).thenReturn(applicationComponentTest);

        ((ApplicationComponentTest) Injector.getApplicationComponent()).inject(this);
    }

    @Test
    @Ignore
    public void commonService_isFirstTime() {
        int ID_ENVIRONMENT = 108;
        int ID_LABEL = 8888;
        String LABEL_ENVIRONMENT = "myEnvironment";

        when(preferenceService.isFirstRun()).thenReturn(true);
        when(commonService.getString(ID_LABEL)).thenReturn(LABEL_ENVIRONMENT);
        when(environmentService.add(LABEL_ENVIRONMENT)).thenReturn(Observable.just(ID_ENVIRONMENT));
        doCallRealMethod().when(commonService).initApplication();

        System.out.println(preferenceService.isFirstRun());

        commonService.initApplication();

        verify(environmentService, times(1)).add(LABEL_ENVIRONMENT);
        verify(preferenceService, times(1)).initFirstRun();
    }

    @Test
    @Ignore
    public void commonService_isNotFirstTime() {

    }

}
