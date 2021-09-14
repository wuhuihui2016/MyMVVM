import groovy.xml.MarkupBuilder

final String xml = '''
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.jvm_demo_20200601">
    <test>12345</test>
    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name=".MainActivity2">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
'''

//解析XML数据
def xmlSluper = new XmlSlurper()
def result = xmlSluper.parseText(xml)
println result.@package
println result.test.text()
//读取有域名空间的节点
result.declareNamespace('android': 'http://schemas.android.com/apk/res/android')
println result.application.@'android:allowBackup'
println result.application.activity[0].@'android:name'
println result.application.activity[1].@'android:name'

//遍历XML节点
result.application.activity.each { activity ->
    println activity.@'android:name'
}

def sw = new StringWriter()
def xmlBuilder = new MarkupBuilder(sw)
xmlBuilder.html() {
    title(id: '123', name: 'android', 'xml生成') {
        person()
    }
    body(name: 'java') {
        activity(id: '001', class: 'MainActivity', 'abc')
        activity(id: '002', class: 'SecActivity', 'abc')
    }
}
println sw












