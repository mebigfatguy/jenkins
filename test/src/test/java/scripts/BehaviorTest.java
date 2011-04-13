/*
 * The MIT License
 *
 * Copyright (c) 2011, CloudBees, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package scripts;

import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jvnet.hudson.test.HudsonTestCase;

/**
 * Tests <tt>behaviour.js</tt>
 *
 * @author Kohsuke Kawaguchi
 */
public class BehaviorTest extends HudsonTestCase {
    public void testCssSelectors() throws Exception {
        HtmlPage p = createWebClient().goTo("self/testCssSelectors");

        // basic class selector, that we use the most often
        assertEquals(2,asInt(p.executeJavaScript("findElementsBySelector($('test1'),'.a',true).length")));
        assertEquals(1,asInt(p.executeJavaScript("findElementsBySelector($('test1'),'.a',false).length")));

        // 'includeSelf' should only affect the first axis and not afterward
        assertEquals(1,asInt(p.executeJavaScript("findElementsBySelector($('test2'),'.a .b',true).length")));
        assertEquals(1,asInt(p.executeJavaScript("findElementsBySelector($('test2'),'.a .b',false).length")));

        // tag.class. Should exclude itself anyway even if it's included
        assertEquals(1,asInt(p.executeJavaScript("findElementsBySelector($('test3'),'P.a',true).length")));
        assertEquals(1,asInt(p.executeJavaScript("findElementsBySelector($('test3'),'P.a',false).length")));
    }

    private int asInt(ScriptResult r) {
        return ((Double)r.getJavaScriptResult()).intValue();
    }
}
