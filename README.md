# android_Disp_JANCode
バーコードフォーマットがJANコードに決まったのでFragmentにて再構築

## JANコードにフォーマットを変える  

JANコードは、EAN-13の別称になる。  
----  
日本で最も普及している商品識別コードであり、日本ではJANコードと呼称されています。  
EAN(JAN)コードから生成されたバーコードシンボルは市販される多くの商品に印刷または貼付されており、  
POSシステムや在庫管理システムなどで価格や商品名を検索するためのキーとして使われています。  
----  
実装方法（HomeFragment.java）

            EAN13Writer writer = new EAN13Writer();

            //EAN13Writer#encode()には以下の情報を渡す
            // (1)エンコード対象の文字列、バーコードシンボルに埋め込みたい情報
            // (2)出力するバーコードの書式
            // (3)イメージの幅
            // (4)イメージの高さ
            bitData  = writer.encode(targetData, BarcodeFormat.EAN_13, width, height);
            
## fragmentにて、実装

