package obfuscation.datautils;

import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

/**
 * Simple visitor implementation for visiting String Literal Expressions nodes and encrypts them
 */
public class StringEncryptionVisitor  extends VoidVisitorAdapter<Void>{
    private EncryptionHelper encryptionHelper = new EncryptionHelper();
    private String key = "";
    private String keyHalf1 = "";
    private String keyHalf2 = "";
    private String initVector = "";
    private String ivHalf1 = "";
    private String ivHalf2 = "";

    @Override
    public void visit(StringLiteralExpr s, Void arg) {
        String encryptedString = encryptionHelper.encrypt(key, initVector, s.getValue());
        s.setValue(encryptedString);

        super.visit(s, arg);
    }

    public void setKeyAndIv(){
        this.key = encryptionHelper.generateString(16);
        this.initVector = encryptionHelper.generateString(16);
//        System.out.println("Generated key: " + this.key);
//        System.out.println("Generated iv: " + this.initVector);
    }

    public void setHalves(){
        String[] keyHalves = encryptionHelper.generateKeyHalves(this.key);
        this.keyHalf1 = keyHalves[0];
        this.keyHalf2 = keyHalves[1];

        String[] ivHalves = encryptionHelper.generateKeyHalves(this.initVector);
        this.ivHalf1 = ivHalves[0];
        this.ivHalf2 = ivHalves[1];
    }

    public String getKey() {
        return key;
    }

    public String getInitVector() {
        return initVector;
    }

    public String getKeyHalf1() {
        return keyHalf1;
    }

    public String getKeyHalf2() {
        return keyHalf2;
    }

    public String getIvHalf1() {
        return ivHalf1;
    }

    public String getIvHalf2() {
        return ivHalf2;
    }
}

