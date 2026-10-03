# CodeAlpha_StockTradingPlatform

A robust Java console application developed for the CodeAlpha Java Programming Internship[cite: 1]. The platform simulates a real-time stock trading environment, enabling users to evaluate market prices, execute buy and sell orders, manage an investment portfolio, and track financial performance with persistent storage[cite: 2].

## Key Features
- **Market Data Display:** View dynamic quotes and real-time pricing across listed stocks[cite: 2].
- **Trading Operations:** Execute buy and sell orders with integrated cash balance and share holding validations[cite: 2].
- **Portfolio Tracking:** Track cash balances, share counts, individual position values, and total portfolio net worth over time[cite: 2].
- **OOP Architecture:** Built using object-oriented principles with modular classes for `Stock`, `Market`, and `User`[cite: 2].
- **File I/O Persistence:** Automatically saves and reloads user balance and portfolio data using CSV storage[cite: 2].

## Project Structure
```text
CodeAlpha_StockTradingPlatform/
├── src/
│   ├── Stock.java
│   ├── Market.java
│   ├── User.java
│   └── Main.java
├── portfolio.csv
└── README.md