package interceptors;

import domain.Kweet;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class KweetLoggingInterceptor {
    @AroundInvoke
    public Object interceptKweet(InvocationContext context) throws Exception {
        Kweet kweet = (Kweet) context.getParameters()[0];

        if (kweet != null) {
            String text = kweet.getText();
            System.out.println(text);

            kweet.setText(text
                    .replaceAll("fuck", "vervelende")
                    .replaceAll("klootzak", "onaardige")
                    .replaceAll("idioot", "stommerd"));

            context.setParameters(new Object[]{kweet});
        }

        return context.proceed();
    }
}
