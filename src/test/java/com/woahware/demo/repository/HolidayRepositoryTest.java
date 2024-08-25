package com.woahware.demo.repository;


import com.woahware.demo.models.Holiday;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
public class HolidayRepositoryTest extends GenericRepositoryTest<Holiday, HolidayRepository> {


}
