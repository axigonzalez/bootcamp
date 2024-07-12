import { Component } from '@angular/core';

@Component({
  selector: 'app-calculator',
  standalone: true,
  imports: [],
  templateUrl: './calculator.component.html',
  styleUrl: './calculator.component.css'
})
export class CalculatorComponent {
  display: string = '0';
  currentOperand: string = '0';
  previousOperand: string = '';
  operation: string | null = null;

  appendNumber(number: string) {
    if (number === '±') {
      if (this.currentOperand.startsWith('-')) {
        this.currentOperand = this.currentOperand.slice(1);
      } else {
        this.currentOperand = '-' + this.currentOperand;
      }
    } else if (number === '.' && this.currentOperand.includes('.')) {
      return;
    } else {
      if (this.currentOperand === '0' && number !== '.') {
        this.currentOperand = number;
      } else {
        this.currentOperand = this.currentOperand.toString() + number.toString();
      }
    }
    this.updateDisplay();
  }

  chooseOperation(operation: string) {
    if (this.currentOperand === '') return;
    if (this.previousOperand !== '') {
      this.compute();
    }
    this.operation = operation;
    this.previousOperand = this.currentOperand;
    this.currentOperand = '';
  }

  compute() {
    let result;
    const prev = parseFloat(this.previousOperand);
    const current = parseFloat(this.currentOperand);
    if (isNaN(prev) || isNaN(current) && this.operation !== '√') return;

    switch (this.operation) {
      case '+':
        result = prev + current;
        break;
      case '-':
        result = prev - current;
        break;
      case '*':
        result = prev * current;
        break;
      case '/':
        result = prev / current;
        break;
      case '^':
        result = Math.pow(prev, current);
        break;
      default:
        return;
    }
    this.currentOperand = this.roundResult(result).toString();
    this.operation = null;
    this.previousOperand = '';
    this.updateDisplay();
  }

  sqrt() {
    const current = parseFloat(this.currentOperand);
    if (isNaN(current) || current < 0) return;
    this.currentOperand = this.roundResult(Math.sqrt(current)).toString();
    this.updateDisplay();
  }

  roundResult(number: number): number {
    return Math.round(number * 1e10) / 1e10;
  }

  clear() {
    this.currentOperand = '0';
    this.previousOperand = '';
    this.operation = null;
    this.updateDisplay();
  }

  delete() {
    if (this.currentOperand.length === 1) {
      this.currentOperand = '0';
    } else {
      this.currentOperand = this.currentOperand.toString().slice(0, -1);
    }
    this.updateDisplay();
  }

  updateDisplay() {
    this.display = this.currentOperand;
  }
}