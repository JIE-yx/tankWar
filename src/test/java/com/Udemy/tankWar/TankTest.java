package com.Udemy.tankWar;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TankTest {

    @Test
    void getImage() {

        for ( Direction direction : Direction.values() ){
            assertTrue( new Tank(0,0,direction,false)
                            .getImage().getHeight(null) > 0
                    ,direction+" cannot get the valid image"  );
            assertTrue( new Tank(0,0,direction,false)
                            .getImage().getHeight(null) > 0
                    ,direction+" cannot get the valid image"  );
        }
    }
}