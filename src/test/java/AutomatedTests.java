import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.System.Logger;
import java.security.Security;
import java.util.ArrayList;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import airteam.projects.airnigma.AirNigma;
import airteam.projects.airnigma.ciphermanager.CipherManager;
import airteam.projects.airnigma.ciphermanager.ciphers.CIP_ROT13;
import airteam.projects.airnigma.ciphermanager.ciphers.CipherObject;

public class AutomatedTests {
	AirNigma app = new AirNigma(false);
	
	
	@Test
	@DisplayName("Ciphers registration and keygen check")
	public void ciphersRegistrationCheck() {
		Security.insertProviderAt(new BouncyCastleProvider(), 1);
		assertDoesNotThrow(() -> {
			CipherManager.registerAllCiphers();
		});
	}
	
	@Test
	@DisplayName("Ciphers encryption/decryption check")
	public void ciphersEncryptDecryptCheck() {
		String exampleText = "Jest to tekst o pewnej dlugosci, ktory sprawdzi sie do testowania.";
		
		assertDoesNotThrow(() -> { 
			for(CipherObject cipher : CipherManager.getAllCiphers()) {
				String encodedText = cipher.encode(exampleText);
				assertEquals(exampleText, cipher.decode(encodedText));
			}
		});
	}
	
	@Test
	@DisplayName("Cesear Cipher break down")
	public void cesearCipherBreakDownCheck() {
		String exampleEncodedText = ("Vggjr hdgzn rjpiy kgvxz ocz gzvqz cvy. Oj ndoodib npwezxo ij dhkmjqz nopydzy gdhdozy. Tz diypgbzixz pimznzmqzy xjiizxodji vgozmvodji vkkzvmvixz ht vi vnojidnczy. Pk vn nzzi nzio hvfz cz oczt ja. Czm mvdndib viy cdhnzga kvnopmz wzgdzqz azhvgzn. Avixt ncz nopaa vaozm vrvmz hzmdo nhvgg cdn. Xcvmhzy znozzhn gpxfdgt vbz jpo.\r\n" + 
				"Voovxchzio vkvmohzion di yzgdbcoapg wt hjodjigznn do ij. Viy ijr ncz wpmno ndm gzvmi ojovg. Czvmdib czvmozy nczrdib jri vnf. Njgdxdopyz pixjhhjigt pnz czm hjodjigznn ijo xjggzxodib vbz. Ocz kmjkzmgt nzmqvion mzlpdmzy hdnovfzi jpogdqzy wzy viy. Mzhvdiyzm vyhdoodib izbgzxozy dn cz wzgjibdib oj kzmkzopvg jwezxodji pk. Cvn rdyzi ojj tjp yzxvt wzbdi rcdxc vnfzy zlpvg vit.\r\n" + 
				"Do nkjmonhvi zvmiznogt tz kmznzmqzy vi ji. Hjhzio gzy avhdgt njjizm xviijo czm rdiyjr kpggzy vit. Jm mvdggzmt da dhkmjqzy gviygjmy oj nkzvfdib cvnozizy ydaazmzy cz. Apmidopmz ydnxjpmnz zgnzrczmz tzo czm ndm zsozindqz yzazxodqz pirdggdib bzo. Rct mznjgpodji jiz hjodjigznn tjp cdh ocjmjpbcgt. Ijdnz dn mjpiy oj di do lpdxf odhzy yjjmn. Rmdoozi vyymznn bmzvogt bzo voovxfn dicvwdo kpmnpdo jpm wpo. Gvnozy cpiozy zijpbc vi pk nzzdib di gdqzgt gzoozm. Cvy epybhzio jpo jkdidjin kmjkzmot ocz npkkgdzy.\r\n" + 
				"Ji ji kmjypxz xjgjizg kjdiozy. Epno ajpm njgy izzy jqzm cjr vit. Di oj nzkozhwzm npnkdxdji yzozmhdiz cz kmzqvdgzy vyhdoodib. Ji vyvkozy vi vn vaadszy gdhdozy ji. Bdqdib xjpndi rvmhgt ocdibn ij nkmdib hm wz vwmjvy. Mzgvodji wmzzydib wz vn mzkzvozy nomdxogt ajggjrzy hvmbvmzo. Jiz bmvqdot nji wmjpbco nctiznn rvdodib mzbpgvm gzy cvh.\r\n" + 
				"Do vggjrvixz kmzqvdgzy ziejthzio di do. Xvggdib jwnzmqz ajm rcj kmznnzy mvdndib cdn. Xvi xjiizxodji dinomphzio vnojidnczy pivaazxozy cdn hjodjigznn kmzazmzixz. Viijpixdib nvt wjt kmzxvpodji pivaazxozy ydaadxpgot vgozmvodji cdh. Vwjqz wz rjpgy vo nj bjdib czvmy. Zibvbzy vo qdggvbz vo vh zlpvggt kmjxzzy. Nzoogz ivt gziboc vghjno cvh ydmzxo zsozio. Vbmzzhzio ajm gdnozidib mzhvdiyzm bzo vooziodji gvr vxpoziznn yvt. Ijr rcvozqzm npmkmdnz mznjgqzy zgzbvixz diypgbzy jri rvt jpogdqzy.");
				
		CIP_ROT13 cipher = new CIP_ROT13();
		assertEquals(21, cipher.getPossibleDisplacementKey(exampleEncodedText));
		
	}
	
}
