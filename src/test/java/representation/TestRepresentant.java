package representation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestRepresentant {
	// Quelques constantes
	private static final float FIXE_BASTIDE = 1000f;
	private static final float INDEMNITE_OCCITANIE = 200f;
	
	private Representant r; // L'objet à tester
	private ZoneGeographique occitanie;
	
	@BeforeEach
	public void setUp() {
		// Initialiser les objets utilisés dans les tests
		occitanie = new ZoneGeographique(1, "Occitanie");
		occitanie.setIndemniteRepas(INDEMNITE_OCCITANIE);

		r = new Representant(36, "Bastide", "Rémi", occitanie);	
		r.setSalaireFixe(FIXE_BASTIDE);				
	}
	
	@Test
	public void testSalaireMensuel() {
		float CA = 50000f;
		float POURCENTAGE= 0.1f; // 10% de pourcentage sur CA
		// On enregistre un CA pour le mois 0 (janvier)
		r.enregistrerCA(0, CA);
		
		// On calcule son salaire pour le mois 0 avec 10% de part sur CA
		float salaire = r.salaireMensuel(0, POURCENTAGE);
		
		// A quel résultat on s'attend ?
		
		assertEquals(// Comparaison de "float"
			// valeur attendue
			FIXE_BASTIDE + INDEMNITE_OCCITANIE + CA * POURCENTAGE,
			// Valeur calculée
			salaire,
			// Marge d'erreur tolérée
			0.001,
			// Message si erreur
			"Le salaire mensuel est incorrect"
		); 
	}

	@Test
	public void testCAParDefaut() {
		float POURCENTAGE= 0.1f; // 10% de pourcentage sur CA
		
		// On n'enregistre aucun CA
		//r.enregistrerCA(0, 10000f);
		
		// On calcule son salaire pour le mois 0 avec 10% de part sur CA
		float salaire = r.salaireMensuel(0, POURCENTAGE);
		
		// A quel résultat on s'attend ?
		// Le CA du mois doit avoir été initialisé à 0
		
		assertEquals(
			FIXE_BASTIDE + INDEMNITE_OCCITANIE, 
			salaire, 
			0.001,
			"Le CA n'est pas correctement initialisé"
		);
	}

	@Test
	public void testCANegatifImpossible() {
		
		try {
			// On enregistre un CA négatif, que doit-il se passer ?
			// On s'attend à recevoir une exception
			r.enregistrerCA(0, -10000f);
			// Si on arrive ici, c'est une erreur, le test doit échouer
			fail("Un CA négatif doit générer une exception"); // Forcer l'échec du test			
		} catch (IllegalArgumentException e) {
			// Si on arrive ici, c'est normal, c'est le comportement attendu
		}

	}
        
        @Test
    public void testMoisInvalideInferieurEnregistrerCA(){
        assertThrows(IllegalArgumentException.class, () -> {
                r.enregistrerCA(-1, FIXE_BASTIDE);
        });
    }
    
    @Test
    public void testMoisInvalideSupérieurEnregistrerCA(){
        assertThrows(IllegalArgumentException.class, () -> {
                r.enregistrerCA(12, FIXE_BASTIDE);
        });
    }
    
    @Test
    public void testMoisInvalideInferieurSalaireMensuel(){
        assertThrows(IllegalArgumentException.class, () -> {
            r.salaireMensuel(-1, 0.1f);
        });
    }
    
    @Test
    public void testMoisInvalideSupérieurSalaireMensuel(){
        assertThrows(IllegalArgumentException.class, () -> {
                 r.salaireMensuel(-1, 0.1f);
        });
    }

    @Test
    public void testMontantInvalideEnregistrerCA(){
        assertThrows(IllegalArgumentException.class, () -> {
                r.enregistrerCA(0, -1);
        });
    }
    
    @Test
    public void testPourcentageInvalideSalaireMensuels(){
            assertThrows(IllegalArgumentException.class, () -> {
                    r.salaireMensuel(0, -0.1f);
            });
        }
	
    @Test
    public void testToStringRepresentant() {
    	assertEquals("Representant{numero=36, nom=Bastide, prenom=Rémi}",r.toString());
    }
    
    @Test
    public void testToStringZG() {
    	assertEquals("ZoneGeographique{numero=1, nom=Occitanie, indemniteRepas=200.0}", occitanie.toString());
    }
    
    @Test
    public void testAllGetterSetterRepresentant() {
    	ZoneGeographique aquitaine = new ZoneGeographique(2,"Aquitaine");
    	r.setSecteur(aquitaine);
    	r.setAdresse("Castres");
    	assertEquals(36,r.getNumero());
    	assertEquals("Bastide",r.getNom());
    	assertEquals("Rémi",r.getPrenom());
    	assertEquals(1000.0,r.getSalaireFixe());
    	assertEquals("Castres",r.getAdresse());
    	assertEquals(aquitaine,r.getSecteur());
    }
    
    @Test
    public void testAllGetterSetterZg() {
    	assertEquals(1,occitanie.getNumero());
    	assertEquals("Occitanie",occitanie.getNom());
    }
}
