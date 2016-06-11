package com.w67clement.mineapi;

import com.w67clement.mineapi.utils.MineAPIUtils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import name.fraser.neil.plaintext.diff_match_patch;
import name.fraser.neil.plaintext.diff_match_patch.Patch;

/**
 * Created by w67clement on 10/06/2016.
 * <p>
 * Class of project: MineAPI
 */
public class MinePatcher
{
    private final diff_match_patch patcher = new diff_match_patch();

    public MinePatcher()
    {
    }

    public boolean patchFile(File patch) throws IOException
    {
        Path pathPatch = patch.toPath();
        String javaPathFile = pathPatch.toString().replace(".patch", ".java").replace("patches", "sources");
        File javaFile = new File(javaPathFile);
        if (!javaFile.exists())
        {
            javaFile.getParentFile().mkdirs();
            String baseUrl = "https://www.raw.githubusercontent.com/w67clement/MineAPI/master/src/main/java/";
            String url = baseUrl;

            for (int i = 0; i < javaPathFile.length(); i++)
            {
                char a = javaPathFile.charAt(i);
                if (a == 's')
                {
                    int i2 = i + 1;
                    if (javaPathFile.charAt(i2) == 'o')
                    {
                        i2 += 5;
                        if (javaPathFile.charAt(i2) == 's' && javaPathFile.charAt((i2 + 1)) == File.separatorChar)
                        {
                            url += javaPathFile.substring(i2 + 1);
                        }
                    }
                }
            }

            MineAPIUtils.download(url, javaFile);
        }
        String patchString = "";
        for (String line : Files.readAllLines(pathPatch))
        {
            patchString += line + "\n";
        }
        List<Patch> patches = patcher.patch_fromText(patchString);
        String srcString = "";
        for (String line : Files.readAllLines(javaFile.toPath()))
        {
            srcString += line + "\n";
        }
        String result = (String) patcher.patch_apply((LinkedList<Patch>) patches, srcString)[0];
        File patchedFile = new File(javaPathFile.replace("sources", "patched"));
        patchedFile.getParentFile().mkdirs();
        FileWriter writer = new FileWriter(patchedFile);
        writer.append(result);
        Files.move(pathPatch, Paths.get(pathPatch.toString().replace("patches", "used")));
        return true;
    }
}
