package compile
import util.CLI
import scala.util.parsing.input.Reader
import scala.util.parsing.input.StreamReader
import scala.collection.immutable.PagedSeq
import java.io._
import scala.io.Source
import scala.collection.mutable.{StringBuilder, ListBuffer}

// Begin parser/scanner imports
import antlr.CommonAST
import antlr.collections.AST
import antlr.Token
import edu.mit.compilers.grammar.{ DecafParser, DecafParserTokenTypes, DecafScanner, DecafScannerTokenTypes }

object Compiler {
  val tokenMap = Map(DecafScannerTokenTypes.ID -> "IDENTIFIER")
  def main(args: Array[String]): Unit = {
    CLI.parse(args, Array[String]());
 
    if (CLI.target == CLI.Action.SCAN) {
      scan(CLI.infile)
      System.exit(0)
    } else if (CLI.target == CLI.Action.PARSE) {
        if(parse(CLI.infile) == null) {
          System.exit(1)
        }
        System.exit(0)
    }
  }

  def scan(fileName: String) {
    try {
      val inputStream: FileInputStream = new java.io.FileInputStream(fileName)
      val scanner = new DecafScanner(new DataInputStream(inputStream))
      scanner.setTrace(CLI.debug)
      consume(scanner)
    } catch {
      case ex: Exception => println(ex)// TODO
    }
  }

  def consume(scanner: DecafScanner) {
    val head = scanner.nextToken()
    if (head.getType() == DecafScannerTokenTypes.EOF) {
      return
    }
    val tokenType = " " + tokenMap.getOrElse(head.getType(), "")
    println(head.getLine() + tokenType + " " + head.getText())
    return consume(scanner)
  }


  def parse(fileName: String): CommonAST  = {
    /** 
    Parse the file specified by the filename. Eventually, this method
    may return a type specific to your compiler.
    */
    var inputStream : java.io.FileInputStream = null
    try {
      inputStream = new java.io.FileInputStream(fileName)
    } catch {
      case f: FileNotFoundException => { println("File " + fileName + " does not exist"); return null }
    }
    try {
      val scanner = new DecafScanner(new DataInputStream(inputStream))
      val parser = new DecafParser(scanner);

      parser.setTrace(CLI.debug)
      parser.program()
      val t = parser.getAST().asInstanceOf[CommonAST]
      if (parser.getError()) {
        print("[ERROR] Parse failed\n")
        return null
      } else if (CLI.debug){
        print(t.toStringList())
      }
      t
    } catch {
      case e: Exception => println(CLI.infile + " " + e)
      null
    } 
  }
}
