public class SmartCamaraTest{

	public SmartCamaraTest(){

	}




	public void testeConstrutor(){
		SmartCamera SmartCamera1 = new SmartCamera();
		assertTrue(SmartCamera1!=null);
		SmartCamera SmartCamera1 = new SmartCamera(simulador,"SmartCamera1",5,OFF,15,"Panasonic","RUM");
		assertTrue(SmartCamera1!=null);
		SmartCamera SmartCamera2 = new SmartCamera(SmartCamera1);
		assertTrue(SmartCamera2!=null);
	}

	public void testGetVolume() {
        SmartCamera SmartCamera1 = new SmartCamera(simulador,"SmartCamera1",5,OFF,15,"Panasonic","RUM");
        assertEquals(SmartCamera1)
        SmartCamera SmartCamera2 = new SmartCamera(simulador,"SmartCamera1",5,OFF,SmartCamera.MAX,"Panasonic","RUM");
        assertEquals(100, SmartCamera2.getVolume());
        SmartCamera SmartCamera3 = new SmartCamera(simulador,"SmartCamera1",5,OFF,-15,"Panasonic","RUM");
        assertEquals(0, smartSpe1.getVolume());
        SmartCamera SmartCamera4 = new SmartCamera(simulador,"SmartCamera1",5,OFF,110,"Panasonic","RUM");
        assertEquals(100, smartSpe4.getVolume());
        SmartCamera SmartCamera5 = new SmartCamera();
        assertEquals(0, SmartCamera5.getVolume());
    }

    public void testGetMarca() {
        SmartCamera SmartCamera1 = new SmartCamera(simulador,"SmartCamera1",5,OFF,15,"Panasonic","RUM");
        assertEquals("Panasonic", SmartCamera1.getMarca());
        SmartCamera SmartCamera2 = new SmartCamera();
        assertEquals("N/A", SmartCamera2.getMarca());
    }

    public void testGetRadio() {
        SmartCamera SmartCamera1 = new SmartCamera(simulador,"SmartCamera1",5,OFF,15,"Panasonic","RUM");
        assertEquals("RUM", SmartCamera1.getRadio());
        SmartCamera SmartCamera2 = new SmartCamera();
        assertEquals("N/A", SmartCamera2.getMarca());
    }

    public void testSetVolume() {
        SmartCamera SmartCamera1 = new SmartCamera(simulador,"SmartCamera1",5,OFF,13,"Panasonic","RUM");
        SmartCamera1.volumeUp();
        SmartCamera1.volumeUp();
        assertEquals(15, SmartCamera1.getVolume());
        for (int i=0; i<25; i++) SmartCamera1.volumeUp();
        assertEquals(40, SmartCamera1.getVolume());

        for (int i=0; i<10; i++) SmartCamera1.volumeDown();
        assertEquals(30, SmartCamera1.getVolume());
        SmartCamera1.setVolume(50);
        assertEquals(50,SmartCamera1.getVolume());
        SmartCamera1.setVolume(150);
        assertEquals(0,SmartCamera1.getVolume());
        SmartCamera1.setVolume(-50);
        assertEquals(0,SmartCamera1.getVolume());
    }



    /*
    Não sei se é suposto fazer testes para o criarSmartCamera.
    Se for, o que é que é o scanner?
    */










}