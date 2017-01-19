import com.atom.hibernateSwing.model.ImageWrapper;
import com.atom.hibernateSwing.model.Products;
import org.junit.*;
import org.junit.rules.Timeout;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.Assert.*;


/**
 * Created by bb on 19.01.2017.
 */
public class AdditionalTest {


    @Test
    public void imageAdd()
    {
        String nameGood = "Merry Poppins";
        String imgPath = "C:\\test.png";            // the local address of the test image file (please, change it for your own real path)

        Products myPr = new Products();

        myPr.setName(nameGood);
        myPr.addImage(createImage(imgPath));
        myPr.addImage(createImage(imgPath));

        // conditions
        assertEquals(2,myPr.getImages().size());

    }

    private ImageWrapper createImage(String path)
    {
        ImageWrapper imageWrapper = new ImageWrapper();

        File file = new File(path);
        byte[] imageData = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(imageData);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        imageWrapper.setName("FrontView.jpeg");
        imageWrapper.setData(imageData);
        return imageWrapper;
    }

}
