//
// Copyright SpiRITlab - The SparkFHE project.
// https://github.com/SpiRITlab
//

package spiritlab.sparkfhe.example.basic;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.spiritlab.sparkfhe.SparkFHESetup;
import org.apache.spark.sql.SparkSession;
import spiritlab.sparkfhe.api.Ciphertext;
import spiritlab.sparkfhe.api.FHELibrary;
import spiritlab.sparkfhe.api.Plaintext;
import spiritlab.sparkfhe.api.SparkFHE;
import spiritlab.sparkfhe.example.Config;

import java.io.File;

/**
 * This is an example for SparkFHE project. Created to test the cryto-libraries basic function - generating a key.
 */
public class KeyGenExampleWithHDFS {

    public static void main(String args[]) {
        Logger.getLogger("org").setLevel(Level.ERROR);
        Logger.getLogger("akka").setLevel(Level.ERROR);

        int slices = 2;
        SparkConf sparkConf = new SparkConf();
        sparkConf.setAppName("KeyGenExample");

        if ( "local".equalsIgnoreCase(args[0]) ) {
            sparkConf.setMaster("local");
        } else {
            slices=Integer.parseInt(args[0]);
            Config.update_current_directory(sparkConf.get("spark.mesos.executor.home"));
            System.out.println("CURRENT_DIRECTORY = "+Config.get_current_directory());
        }
        // Creating a session to Spark. The session allows the creation of the
        // various data abstractions such as RDDs, DataFrame, and more.
        SparkSession spark = SparkSession.builder().config(sparkConf).getOrCreate();

        // Creating spark context which allows the communication with worker nodes
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

        // Load C++ shared library
        SparkFHESetup.setup();
        // Create SparkFHE object with HElib, a library that implements homomorphic encryption
        SparkFHE.init(FHELibrary.HELIB);

        // Creates the directory named by the pathname - current_directiory/gen/keys,
        // and including any necessary parent directories.
        //new File(Config.get_keys_directory()).mkdirs();

        // Using the object created to call the C++ function to generate the keys.
        SparkFHE.getInstance().generate_key_pair(
                Config.get_default_crypto_params_file(FHELibrary.HELIB),
                Config.get_local_HDFS_path(Config.DEFAULT_PUBLIC_KEY_FILE),
                Config.get_local_HDFS_path(Config.DEFAULT_SECRET_KEY_FILE));
      
        // Encrypting the literal 1, and decrypting it to verify the keys' accuracy.
        String inputNumberString="1";
        Plaintext inputNumberPtxt = new Plaintext(inputNumberString);

        Ciphertext ctxt = SparkFHE.getInstance().encrypt(inputNumberPtxt);
        Plaintext ptxt = SparkFHE.getInstance().decrypt(ctxt);

        // Printing out the result
        System.out.println("InputNumber="+inputNumberString + ", result of dec(enc(InputNumber))="+ptxt.toString());
    }


}