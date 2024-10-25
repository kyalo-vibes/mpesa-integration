# M-Pesa Integration Project

## Overview

This project provides an integration with M-Pesa, a popular mobile payment service in East Africa. The project allows for seamless interaction with the M-Pesa API, enabling developers to build applications that can send and receive payments, query transaction status, and more.

## Features

* Integration with M-Pesa API for payment processing
* Support for various payment methods, including STK Push and C2B
* Transaction status querying and notification handling
* Configuration options for customizing API endpoints, credentials, and more

## Getting Started

### Prerequisites

* Java 8 or later
* Maven 3.3.2 or later
* M-Pesa API credentials (consumer key, consumer secret, access key, and access token)

### Installation

1. Clone the repository: `git clone https://github.com/your-repo/mpesa-integration.git`
2. Navigate to the project directory: `cd mpesa-integration`
3. Build the project using Maven: `mvn clean install`
4. Configure the API credentials in `application-local.properties`

## Usage

### Payment Processing

To process a payment, create an instance of the `MpesaClient` class and use the `sendPayment` method:

```java
MpesaClient client = new MpesaClient();
PaymentRequest request = new PaymentRequest();
request.setAmount(100.0);
request.setPhoneNumber("+254712345678");
request.setTransactionDesc("Test payment");

StkPushResponse response = client.sendPayment(request);
```

## Configuration

The project uses a configuration file `application-local.properties` to store API credentials and other settings. You can customize the following properties:

* `app.integrations.mpesa.domain`: M-Pesa API endpoint URL
* `app.integrations.mpesa.consumer-key`: Consumer key for API authentication
* `app.integrations.mpesa.consumer-secret`: Consumer secret for API authentication
* `app.integrations.mpesa.access-key`: Access key for API authentication
* `app.integrations.mpesa.access-token`: Access token for API authentication

## Contributing

Contributions are welcome! If you'd like to contribute to the project, please fork the repository and submit a pull request with your changes.
