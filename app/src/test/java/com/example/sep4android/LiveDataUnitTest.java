package com.example.sep4android;

import org.junit.Test;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.*;

import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Model.User;
import com.example.sep4android.RemoteDataSource.AuthentificationDataSource;
import com.example.sep4android.Repository.AuthentificationRepository;
import com.example.sep4android.Repository.Repository;
import com.example.sep4android.ViewModel.Co2DetailsViewModel;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LiveDataUnitTest {
    private final String REAL_BOARD_ID = "0004A30B00259D2C";
    private final String dimDateFrom = "2022-05-01";
    private final String dimDateTo = "2022-05-31";
    private final String fakePassword = "1";
    private final String fakeEmail = "fake@email.uk";
    private final String trueEmail = "policja@gov.pl";
    Repository repository = Repository.getInstance();
    AuthentificationDataSource auth = new AuthentificationDataSource();
    AuthentificationRepository authRepo= AuthentificationRepository.getInstance(auth);

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test public void checkCO2LiveData(){
        repository.fetchCO2(REAL_BOARD_ID);
        assertNotNull(repository.getCarbonDioxideValueLiveData());
    }
    @Test public void checkLiveHumidityData(){
        repository.fetchHumidity(REAL_BOARD_ID);
        assertNotNull(repository.getCarbonDioxideValueLiveData());
    }
    @Test public void checkLiveTemperatureData(){
        repository.fetchTemperature(REAL_BOARD_ID);
        assertNotNull(repository.getCarbonDioxideValueLiveData());
    }@Test public void checkLiveLightData(){
        repository.fetchLight(REAL_BOARD_ID);
        assertNotNull(repository.getCarbonDioxideValueLiveData());
    }



    @Test public void checkLogin_fakeCredentials_isNull(){
        assertNull(auth.login(fakeEmail, fakePassword));
    }
    @Test public void checkLogin_wrongPassword_isNull(){
        assertNull(auth.login(trueEmail, fakePassword));
    }



}