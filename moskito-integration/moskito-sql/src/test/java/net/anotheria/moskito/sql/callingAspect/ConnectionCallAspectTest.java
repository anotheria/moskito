package net.anotheria.moskito.sql.callingAspect;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 15.11.18 10:28
 */
public class ConnectionCallAspectTest {

	@Before
	public void init(){
		System.setProperty("JUNITTEST", String.valueOf(true));
	}

	@Test public void testSimpleSelect(){
		String source = "SELECT * from TABLE";
		assertEquals(source, ConnectionCallAspect.removeParametersFromStatement(source));
		assertEquals(source.toLowerCase(), ConnectionCallAspect.removeParametersFromStatement(source).toLowerCase());

		assertEquals("SELECT id, name, column from tAbLe where id =? AND column like ?",
				ConnectionCallAspect.removeParametersFromStatement("SELECT id, name, column from tAbLe where id ='b277efa0-d488-42ee-9346-cf83aad57e4d' AND column like '%B:6F6EDA55C787BAB014ED4A9FB55098358C4758677822D68D857266AF32904AB4C360852E0362A6D7E61D0E99A19F6EBEB8E64DB0BEE6BC311C8602665D388B41E3B414D04AEF7F055F3764744B050A14F2B5E4480E4B62A032B60404FE1F7BA724B8997A8087008C577D291011712E3BDEEE4D8DCBAE7D5B7A144B0B06011E9'"));
		//System.out.println(ConnectionCallAspect.removeParametersFromStatement("SELECT id, name, column from tAbLe where id ='b277efa0-d488-42ee-9346-cf83aad57e4d' AND column like '%B:6F6EDA55C787BAB014ED4A9FB55098358C4758677822D68D857266AF32904AB4C360852E0362A6D7E61D0E99A19F6EBEB8E64DB0BEE6BC311C8602665D388B41E3B414D04AEF7F055F3764744B050A14F2B5E4480E4B62A032B60404FE1F7BA724B8997A8087008C577D291011712E3BDEEE4D8DCBAE7D5B7A144B0B06011E9'"));
	}

	@Test public void testTclBug(){
		assertEquals("SELECT password FROM auth_passwd WHERE accid = ?",
				ConnectionCallAspect.removeParametersFromStatement("SELECT password FROM auth_passwd WHERE accid = '7a5b665f-c7d6-4058-b8a7-f4c95e40122f'"));
	}
}
