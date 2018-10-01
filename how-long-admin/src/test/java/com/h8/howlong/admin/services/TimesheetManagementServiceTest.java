package com.h8.howlong.admin.services;

import com.h8.howlong.services.TimesheetContextService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TimesheetManagementServiceTest {

    private TimesheetManagementService service;

    private TimesheetContextService contextService;

    @BeforeEach
    void setUp() {
        contextService = mock(TimesheetContextService.class);
        service = new TimesheetManagementService(contextService);
    }

    @Test
    public void shouldCallDeleteOnContextService()
            throws TimesheetManagementFailedException {
        //given
        int month = 10;
        int day = 30;

        //when
        when(contextService.deleteWorkday(anyInt(), anyInt()))
                .thenReturn(true);

        service.delete(month, day);

        //then
        ArgumentCaptor<Integer> monthCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> dayCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(contextService, times(1)).deleteWorkday(monthCaptor.capture(), dayCaptor.capture());

        assertThat(monthCaptor.getValue()).isEqualTo(month);
        assertThat(dayCaptor.getValue()).isEqualTo(day);
    }

}