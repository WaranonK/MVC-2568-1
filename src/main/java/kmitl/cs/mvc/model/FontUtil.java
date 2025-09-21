package kmitl.cs.mvc.model;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.Enumeration;

public class FontUtil {
    /**
     * พยายามโหลดฟอนต์จาก resourcePath (เช่น "/fonts/Sarabun-Regular.ttf").
     * ถ้าโหลดไม่ได้ จะใช้ fallbackName (เช่น "Tahoma").
     * จากนั้นตั้งเป็น default ของ UIManager
     */
    public static void setGlobalFont(String resourcePath, String fallbackName, float size) {
        Font base = null;

        // 1) พยายามโหลดจาก resource (jar หรือ classpath)
        if (resourcePath != null) {
            try (InputStream is = FontUtil.class.getResourceAsStream(resourcePath)) {
                if (is != null) {
                    base = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
                    System.out.println("Loaded embedded font: " + resourcePath);
                }
            } catch (Exception ex) {
                System.err.println("Cannot load embedded font: " + ex.getMessage());
            }
        }

        // 2) ถ้าไม่สำเร็จ ให้ใช้ฟอนต์ในระบบ
        if (base == null) {
            try {
                base = new Font(fallbackName, Font.PLAIN, (int)size);
            } catch (Exception ex) {
                base = new Font("Dialog", Font.PLAIN, (int)size);
            }
            System.out.println("Using fallback font: " + base.getFontName());
        }

        // 3) ตั้งเป็น default ให้กับ UIManager (ทุกคอมโพเนนต์)
        setUIFont(new javax.swing.plaf.FontUIResource(base));
    }

    private static void setUIFont(javax.swing.plaf.FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }
}
