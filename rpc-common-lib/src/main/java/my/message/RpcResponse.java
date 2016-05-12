/*
 * 版权所有 (c) 2016. 这是我的第一个测试项目，未经许可不准商用
 */

/*
 * 版权所有 (c) 2016. 这是我的第一个测试项目，未经许可不准商用
 */

package my.message;

/**
 * Created by wangxing on 2016/4/16.
 */
public class RpcResponse {

    private String requestId;
    private Throwable error;
    private Object result;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean isError(){
        return null != error;
    }
}
