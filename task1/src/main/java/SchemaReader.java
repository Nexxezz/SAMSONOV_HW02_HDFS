import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.mapred.FsInput;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.hadoop.Footer;
import org.apache.parquet.hadoop.ParquetFileReader;
import org.apache.parquet.hadoop.metadata.BlockMetaData;
import org.apache.parquet.hadoop.metadata.ParquetMetadata;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.tools.Main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLOutput;

import static org.apache.parquet.format.converter.ParquetMetadataConverter.NO_FILTER;


public class SchemaReader {

    public static void readAvroSchema(Path path) throws IOException {
        GenericRecord record = null;
        long rowsCounter = 0;
        FsInput inputFile = new FsInput(path, new Configuration());
        DatumReader<GenericRecord> datumReader = new GenericDatumReader<>();
        DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(inputFile, datumReader);
        while (dataFileReader.hasNext()) {
            record = dataFileReader.next(record);
            rowsCounter++;
        }
        System.out.println("Number of rows in file :" + rowsCounter);
    }


    public static void readParquetSchema(Path path) throws IOException {
        ParquetMetadata metaData = ParquetFileReader.readFooter(new Configuration(), path, NO_FILTER);
        MessageType schema = metaData.getFileMetaData().getSchema();
        System.out.println(schema.toString());

    }

    public static void main(String[] args) {
        try {
            if (args[0].endsWith(".avro")) {
                {
                    readAvroSchema(new Path(args[0]));
                    System.out.println("avro");
                }
            }
            if (args[0].endsWith(".parquet")) {

                readParquetSchema(new Path(args[0]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
