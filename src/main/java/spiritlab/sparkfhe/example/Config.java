//
// Copyright SpiRITlab - The SparkFHE project.
// https://github.com/SpiRITlab
//

package spiritlab.sparkfhe.example;

import spiritlab.sparkfhe.api.FHELibrary;

public class Config {
    private static String Current_Directory=System.getProperty("user.dir");

    private static final String DEFAULT_CRYPTO_PARAMS_DIRECTORY="/SparkFHE-Addon/resources/params";
    private static final String DEFAULT_COMMON_CRYPTO_PARAMS_FILE="CRYPTO_PARAMS_COMMON_TEMPLATE.json";
    private static final String DEFAULT_HELIB_CRYPTO_PARAMS_FILE="CRYPTO_PARAMS_HELIB_TEMPLATE.json";
    private static final String DEFAULT_SEAL_CRYPTO_PARAMS_FILENAME="CRYPTO_PARAMS_SEAL_TEMPLATE.json";
    private static final String DEFAULT_PALISADE_CRYPTO_PARAMS_FILENAME="CRYPTO_PARAMS_PALISADE_TEMPLATE.json";

    private static final String DEFAULT_KEY_DIRECTORY="/gen/keys";
    public static final String DEFAULT_PUBLIC_KEY_FILE="my_public_key.txt";
    public static final String DEFAULT_SECRET_KEY_FILE="my_secret_key.txt";

    private static final String DEFAULT_RECORDS_DIRECTORY="/gen/records";
    public static int NUM_OF_VECTOR_ELEMENTS = 5;

    public static final String Ciphertext_Label = "ctxt";

    public static enum ExecutionEnvironment {LOCAL, CLUSTER;}
    public static ExecutionEnvironment currentExecutionEnvironment = ExecutionEnvironment.LOCAL;

    private static String HDFS_NAME_NODE = "hdfs://localhost:0";
    private static String HDFS_CURRENT_DIRECTORY = "/SparkFHE/HDFSFolder";


    public static void setExecutionEnvironment(String environment) {
        if ("local".equalsIgnoreCase(environment)) {
            currentExecutionEnvironment = ExecutionEnvironment.LOCAL;
        } else {
            currentExecutionEnvironment = ExecutionEnvironment.CLUSTER;
        }
    }

    public static ExecutionEnvironment getExecutionEnvironment() {
        return currentExecutionEnvironment;
    }

    public static void update_current_directory(String CurrentDir) {
        Current_Directory=CurrentDir;
    }

    public static String get_current_directory() {
        switch (currentExecutionEnvironment) {
            case CLUSTER:
                return get_HDFS_path();
            case LOCAL:
            default:
                return Current_Directory;
        }
    }

    public static void set_HDFS_NAME_NODE(String hdfsURL) {
        HDFS_NAME_NODE = hdfsURL;
    }

    public static void set_HDFS_CURRENT_DIRECTORY(String hdfsRemotePath) {
        HDFS_CURRENT_DIRECTORY = hdfsRemotePath + "/";
    }

    public static String get_HDFS_path() {
        return HDFS_NAME_NODE + HDFS_CURRENT_DIRECTORY;
    }

    public static String get_HDFS_path(String filename) {
        return HDFS_NAME_NODE + HDFS_CURRENT_DIRECTORY + filename;
    }

    public static String get_local_HDFS_path(String filename) {
        final String localhost_HDFS_URL = "hdfs://localhost:0";
        final String remote_hdfs_path = "/tmp/SparkFHE/HDFSFolder/";
        return localhost_HDFS_URL + remote_hdfs_path + filename;
    }

    public static String get_keys_directory() {
        return get_current_directory() + DEFAULT_KEY_DIRECTORY;
    }

    public static String get_default_public_key_file() {
        return get_current_directory() + DEFAULT_KEY_DIRECTORY + "/" + DEFAULT_PUBLIC_KEY_FILE;
    }

    public static String get_default_secret_key_file() {
        return get_current_directory() + DEFAULT_KEY_DIRECTORY + "/" + DEFAULT_SECRET_KEY_FILE;
    }

    public static String get_records_directory() {
        return get_current_directory() + DEFAULT_RECORDS_DIRECTORY;
    }

    public static String get_crypto_param_directory() {
        return get_current_directory() + DEFAULT_CRYPTO_PARAMS_DIRECTORY;
    }

    public static String get_default_crypto_params_file(String lib_name) {
        String crypto_param_file = DEFAULT_COMMON_CRYPTO_PARAMS_FILE;
        if(lib_name.equalsIgnoreCase(FHELibrary.HELIB)){
            crypto_param_file = DEFAULT_HELIB_CRYPTO_PARAMS_FILE;
        } else if (lib_name.equalsIgnoreCase(FHELibrary.SEAL)) {
            crypto_param_file = DEFAULT_SEAL_CRYPTO_PARAMS_FILENAME;
        } else if (lib_name.equalsIgnoreCase(FHELibrary.PALISADE)) {
            crypto_param_file = DEFAULT_PALISADE_CRYPTO_PARAMS_FILENAME;
        }

        switch (currentExecutionEnvironment) {
            case CLUSTER:
                return get_HDFS_path() +
                        DEFAULT_CRYPTO_PARAMS_DIRECTORY + "/" +
                        crypto_param_file;
            case LOCAL:
            default:
                return get_current_directory() +
                        DEFAULT_CRYPTO_PARAMS_DIRECTORY + "/" + crypto_param_file;
        }
    }

}
