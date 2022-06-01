package com.example.sep4android;

import org.junit.Test;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.*;

import com.example.sep4android.Repository.AuthentificationRepository;
import com.example.sep4android.Repository.Repository;

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






}