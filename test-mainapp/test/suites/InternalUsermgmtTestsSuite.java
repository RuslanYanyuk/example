package suites;


import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.extensions.cpsuite.ClasspathSuite.ClassnameFilters;
import org.junit.extensions.cpsuite.ClasspathSuite.IncludeJars;
import org.junit.runner.RunWith;


@RunWith(ClasspathSuite.class)
@IncludeJars(true)
@ClassnameFilters({"views.usermgmt.*"})
public class InternalUsermgmtTestsSuite {
	
}