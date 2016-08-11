package mb.project;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.LinkedList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MockTestExample {

  private static final String FAKE_STRING = "HELLO WORLD";

  @Mock
  Context mMockContext;

  @Test
  public void readStringFromContext_LocalizedString() {
    // you can mock concrete classes, not only interfaces
    LinkedList mockedList = mock(LinkedList.class);

// stubbing appears before the actual execution
    when(mockedList.get(0)).thenReturn("first");

// the following prints "first"
    System.out.println(mockedList.get(0));

// the following prints "null" because get(999) was not stubbed
    System.out.println(mockedList.get(999));

    //Additionally, with Mockito we can verify if a method has been called:

// mock creation

// using mock object - it does not throw any "unexpected interaction" exception
    mockedList.add("one");
    mockedList.clear();

// selective, explicit, highly readable verification
    verify(mockedList).add("one"); // If the method was not used previously we will get an Error.
    verify(mockedList).clear();


    /*
    // Given a mocked Context injected into the object under test...
    when(mMockContext.getString(R.string.hello_world))
      .thenReturn(FAKE_STRING);
    ClassUnderTest myObjectUnderTest = new ClassUnderTest(mMockContext);

    // ...when the string is returned from the object under test...
    String result = myObjectUnderTest.getHelloWorldString();

    // ...then the result should be the expected one.
    assertThat(result, is(FAKE_STRING));*/
  }
}
