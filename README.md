# android_Disp_JANCode
バーコードフォーマットがJANコードに決まったのでFragmentにて再構築

## JANコードにフォーマットを変える  

JANコードは、EAN-13の別称になる。  

日本で最も普及している商品識別コードであり、日本ではJANコードと呼称されています。  
EAN(JAN)コードから生成されたバーコードシンボルは市販される多くの商品に印刷または貼付されており、  
POSシステムや在庫管理システムなどで価格や商品名を検索するためのキーとして使われています。  
  
実装方法（HomeFragment.java）

            EAN13Writer writer = new EAN13Writer();

            //EAN13Writer#encode()には以下の情報を渡す
            // (1)エンコード対象の文字列、バーコードシンボルに埋め込みたい情報
            // (2)出力するバーコードの書式
            // (3)イメージの幅
            // (4)イメージの高さ
            bitData  = writer.encode(targetData, BarcodeFormat.EAN_13, width, height);
            
[関連サイトhttps://repo1.maven.org/maven2/](https://develman.net/read-and-write-jancode-with-zxing-java/)    
            

## Zxingライブラリの関数（writeToStream）を使用して、イメージデータにしたい。
　writeToStreamは、MatrixToImageConfigクラスに属しているが、
  com.google.zxing.client.j2seのパッケージのため、依存関係を追加する必要がある。
  現在、色々やっている最中だが、
  jarファイルをネットから取得し、ローカル環境にて追加する方法があったので備忘録として
  残しておく。
  
  ### プロジェクトに追加するには…  
  ①左上の項目をプロジェクトに変え、libsのフォルダを表示させる。    
  ②落としてきたjarファイルをlibsにコピー  
  ③build.gradleのdependenciesに  
  
    implementation fileTree(dir: 'libs', include: ['javase-3.4.0.jar'])

  を追加し、同期を押下
  
  [ローカル上のライブラリ追加方法](https://developer.android.com/studio/build/dependencies?hl=ja)  
  [jarを落とせるサイト](https://repo1.maven.org/maven2/)  
  [関連サイト](https://qiita.com/icchi_h/items/8ce738ce8511ef69c799)
  
・依存関係のライブラリは、同じライブラリのパッケージが複数のバージョンを混ぜているとビルドが通らない。
・ZXINGのライブラリの中で、MatrixToImageConfigクラスはActivityの中でしか使えない。
　（MatrixToImageConfigがprivateのため）
・上記の③は、不要だった。

    implementation fileTree(dir: 'libs', include: ['*.jar'])
    が、定義されているため


## fragmentでのonClickListener実装の注意点
ボタン押下イベントの、onClickメソッドを関数化すると呼ばれなくなってしまう。。。
onStart()などでOnClickListenerの中にて定義するようにする。
（詳細な機構までは深堀していないが、とにかくこれで動いたのでいったんヨシとする）

   @Override
    public void onStart() {
        super.onStart();

        Button button = (Button)getActivity().findViewById(R.id.AddButton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                ：
                }
                catch (Exception e)
                {
                    Log.e("ERROR","例外発生" + e);
                }
            }
        });
        
    
